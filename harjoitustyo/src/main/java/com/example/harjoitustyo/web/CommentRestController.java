package com.example.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.example.harjoitustyo.Views;
import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.Exception.CustomForbiddenException;
import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.Comment;
import com.example.harjoitustyo.domain.CommentRepository;
import com.example.harjoitustyo.domain.LocationRepository;
import com.fasterxml.jackson.annotation.JsonView;
import com.example.harjoitustyo.domain.AppUserRepository;

import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin(originPatterns = "*")
@RestController
public class CommentRestController {

    private CommentRepository coRepository;
    private AppUserRepository auRepository;
    private LocationRepository lRepository;

    public CommentRestController(CommentRepository coRepository, AppUserRepository auRepository,
            LocationRepository lRepository) {
        this.coRepository = coRepository;
        this.auRepository = auRepository;
        this.lRepository = lRepository;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/comments")
    public List<Comment> getAllCities() {
        return (List<Comment>) coRepository.findAll();

    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(Views.Public.class)
    @GetMapping(value = "/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Comment> getComment(@PathVariable Long id) {
        Optional<Comment> comment = coRepository.findById(id);
        if (!comment.isPresent()) {
            throw new CustomNotFoundException("Comment does not exist");
        }
        return comment;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @JsonView(Views.Public.class)
    @PostMapping("/comments")
    public Comment postComment(@RequestBody Comment comment) {
        if (comment.getCommentId() != null) {
            throw new CustomBadRequestException("Do not include commentId");
        } else if (comment.getHeadline() == null || comment.getHeadline().isEmpty()) {
            throw new CustomBadRequestException("Comment headline cannot be empty");
        } else if (comment.getBody() == null || comment.getBody().isEmpty()) {
            throw new CustomBadRequestException("Comment body cannot be empty");
        } else if (comment.getAppUser() == null
                || !auRepository.findById(comment.getAppUser().getAppUserId()).isPresent()) {
            throw new CustomBadRequestException("Wrong or missing AppUser Id");
        } else if (comment.getLocation() == null
                || !lRepository.findById(comment.getLocation().getLocationId()).isPresent()) {
            throw new CustomBadRequestException("Wrong or missing Location Id");
        }
        return coRepository.save(comment);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @JsonView(Views.Public.class)
    @PutMapping("/comments/{id}")
    public Optional<Comment> putComment(@RequestBody Comment newComment, @PathVariable Long id,
            Authentication authentication) {
        Optional<Comment> comment = coRepository.findById(id);
        String currentUser = authentication.getName();
        if (!coRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Comment by id" + id + " does not exist");
        } else if (!comment.get().getAppUser().getUsername().equals(currentUser)) {
            throw new CustomForbiddenException("You do not have permissions to edit comment by the id of " + id);
        } else if (newComment.getHeadline() == null || newComment.getHeadline().isEmpty()) {
            throw new CustomBadRequestException("Comment headline cannot be empty");
        } else if (newComment.getBody() == null || newComment.getBody().isEmpty()) {
            throw new CustomBadRequestException("Comment body cannot be empty");
        } else if (newComment.getAppUser() == null
                || !auRepository.findById(newComment.getAppUser().getAppUserId()).isPresent()) {
            throw new CustomBadRequestException("Wrong or missing AppUser Id");
        } else if (newComment.getLocation() == null
                || !lRepository.findById(newComment.getLocation().getLocationId()).isPresent()) {
            throw new CustomBadRequestException("Wrong or missing Location Id");
        }

        return comment.map(com -> {
            com.setHeadline(newComment.getHeadline());
            com.setBody(newComment.getBody());
            com.setAppUser(newComment.getAppUser());
            com.setLocation(newComment.getLocation());
            return coRepository.save(com);
        });

    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @JsonView(Views.Public.class)
    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id, Authentication authentication) {
        Optional<Comment> comment = coRepository.findById(id);
        String currentUser = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        if (!comment.isPresent()) {
            throw new CustomNotFoundException("Comment by the id " + id + " does not exist");
        } else if (!comment.get().getAppUser().getUsername().equals(currentUser) && !isAdmin) {
            throw new CustomForbiddenException("You do not have permissions to delete comment by the id of " + id);
        }
        coRepository.deleteById(id);
    }
}