package com.example.harjoitustyo.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.AppUser;
import com.example.harjoitustyo.domain.AppUserRepository;
import com.example.harjoitustyo.domain.City;
import com.example.harjoitustyo.domain.Comment;
import com.example.harjoitustyo.domain.Location;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.Region;

import jakarta.servlet.http.HttpServletRequest;

import com.example.harjoitustyo.domain.CommentRepository;

@Controller
public class CommentController {

    private LocationRepository lRepository;
    private CommentRepository coRepository;
    private AppUserRepository auRepository;

    public CommentController(LocationRepository lRepository, CommentRepository coRepository,
            AppUserRepository auRepository) {
        this.lRepository = lRepository;
        this.coRepository = coRepository;
        this.auRepository = auRepository;
    }

    @GetMapping(value = { "/comment/edit/{id}" })
    public String showLocation(@PathVariable("id") Long commentId, Model model, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        Optional<Comment> comment = coRepository.findById(commentId);
        if (!comment.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Comment by the id of " + commentId + " does not exist.");
            return "redirect:" + referer;
        }
        Location location = comment.get().getLocation();
        City city = location.getCity();
        Region region = city.getRegion();

        model.addAttribute("comment", comment.get());
        model.addAttribute("location", location);
        model.addAttribute("city", city);
        model.addAttribute("region", region);
        model.addAttribute("comments", coRepository.findByLocation(location));
        return "commentEdit";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = { "/location/{id}/comment/add" })
    public String addComment(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (!lRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location by the ID of " + id + " does not exist.");
            return "redirect:" + referer;
        }
        Optional<Location> location = lRepository.findById(id);
        if (!location.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location by the ID of " + id + " does not exist.");
            return "redirect:" + referer;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<AppUser> appUser = auRepository.findByUsername(username);
        model.addAttribute("location", location.get());
        model.addAttribute("appUser", appUser.get());
        model.addAttribute("locations", lRepository.findAll());
        model.addAttribute("comment", new Comment());
        return "commentAdd";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/location/{id}/comment/edit/{commentId}")
    public String editComment(@PathVariable("id") Long locationId, @PathVariable("commentId") Long commentId,
            Model model, HttpServletRequest request,
            RedirectAttributes redirectAttributes, Authentication authentication) {
        String referer = request.getHeader("Referer");
        String username = authentication.getName();
        if (!lRepository.existsById(locationId)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location by the ID of " + locationId + " does not exist.");
            return "redirect:" + referer;
        }
        Optional<Comment> comment = coRepository.findById(commentId);
        if (!comment.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Location by the ID of " + locationId + " does not exist.");
            return "redirect:" + referer;
        }
        if (!comment.get().getAppUser().getUsername().equals(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "You can only edit your own comments");
            return "redirect:/";
        }
        Optional<AppUser> appUser = auRepository.findByUsername(username);
        model.addAttribute("appUser", appUser.get());
        model.addAttribute("locations", lRepository.findAll());
        model.addAttribute("comment", comment.get());
        return "commentEdit";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/location/{id}/comment/save")
    public String saveComment(@PathVariable("id") Long id, Comment comment, HttpServletRequest request,
            RedirectAttributes redirectAttributes, Authentication authentication) {
        String referer = request.getHeader("Referer");
        if (comment.getHeadline().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Comment headline is required.");
            return "redirect:" + referer;
        }
        if (comment.getBody().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Comment body is required.");
            return "redirect:" + referer;
        }
        coRepository.save(comment);
        return "redirect:/location/" + id;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/location/{id}/comment/save/{commentId}")
    public String saveEditedComment(@PathVariable("id") Long locationId, @PathVariable("commentId") Long commentId,
            Comment newComment,
            RedirectAttributes redirectAttributes, HttpServletRequest request, Authentication authentication) {
        String referer = request.getHeader("Referer");
        if (newComment.getHeadline().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Comment headline is required.");
            return "redirect:" + referer;
        }
        if (newComment.getBody().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Comment body is required.");
            return "redirect:" + referer;
        }
        if (!coRepository.findById(commentId).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Comment by the id of " + commentId + " does not exist");
            return "redirect:/location" + locationId;
        }

        coRepository.findById(commentId)
                .map(comment -> {
                    comment.setHeadline(newComment.getHeadline());
                    comment.setBody(newComment.getBody());
                    return coRepository.save(comment);
                });

        return "redirect:/location/" + locationId;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping(value = "/comment/delete/{id}")
    public String removeComment(@PathVariable("id") Long commentId, HttpServletRequest request,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        String referer = request.getHeader("Referer");
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        Optional<Comment> comment = coRepository.findById(commentId);
        if (!comment.isPresent()) {
            throw new CustomNotFoundException("Comment by the ID of " + commentId + " does not exist");
        } else if (!isAdmin && !comment.get().getAppUser().getUsername().equals(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Only admins can delete comments by other users.");
            return "redirect:" + referer;
        }
        coRepository.deleteById(commentId);
        return "redirect:" + referer;
    }

}
