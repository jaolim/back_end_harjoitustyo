package com.example.harjoitustyo.web;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.harjoitustyo.Exception.CustomNotFoundException;
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

    public CommentController(LocationRepository lRepository, CommentRepository coRepository) {
        this.lRepository = lRepository;
        this.coRepository = coRepository;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping(value = "/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Long commentId, HttpServletRequest request,
            Authentication authentication, RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        String referer = request.getHeader("Referer");
        boolean isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        Optional<Comment> comment = coRepository.findById(commentId);
        if (!comment.isPresent()){
            throw new CustomNotFoundException("Comment by the ID of " + commentId + " does not exist");
        } else if (!isAdmin && !comment.get().getAppUser().getUsername().equals(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Only admins can delete comments by other users.");
            return "redirect:" + referer;
        }
        coRepository.deleteById(commentId);
        return "redirect:" + referer;
    }

}
