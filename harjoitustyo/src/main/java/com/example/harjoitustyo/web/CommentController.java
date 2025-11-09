package com.example.harjoitustyo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.harjoitustyo.Exception.CustomNotFoundException;
import com.example.harjoitustyo.domain.City;
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

    @GetMapping(value = "/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Long commentId, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        coRepository.deleteById(commentId);
        return "redirect:" + referer;
    }


}
