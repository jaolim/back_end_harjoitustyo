package com.example.harjoitustyo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.harjoitustyo.Exception.CustomBadRequestException;
import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.Comment;
import com.example.harjoitustyo.domain.CommentRepository;
import com.example.harjoitustyo.domain.LocationRepository;
import com.example.harjoitustyo.domain.AppUserRepository;

import org.springframework.web.bind.annotation.ResponseStatus;

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

    @GetMapping(value = "/comments")
    public List<Comment> getAllCities() {
        return (List<Comment>) coRepository.findAll();

    }

    @GetMapping(value = "/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = coRepository.findById(id);
        if (!comment.isPresent()) {
            throw new CustomNotFoundException("Comment does not exist");
        }
        return comment;
    }

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

    @DeleteMapping("/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        if (!coRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Comment by id " + id + " does not exist");
        }
        coRepository.deleteById(id);
    }

    @PutMapping("/comments/{id}")
    public Optional<Comment> putComment(@RequestBody Comment newComment, @PathVariable Long id) {
        if (!coRepository.findById(id).isPresent()) {
            throw new CustomNotFoundException("Comment by id" + id + " does not exist");
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

        return coRepository.findById(id)
                .map(comment -> {
                    comment.setHeadline(newComment.getHeadline());
                    comment.setBody(newComment.getBody());
                    comment.setAppUser(newComment.getAppUser());
                    comment.setLocation(newComment.getLocation());
                    return coRepository.save(comment);
                });

    }
}