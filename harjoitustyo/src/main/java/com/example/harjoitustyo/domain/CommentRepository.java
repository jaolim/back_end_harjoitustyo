package com.example.harjoitustyo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>{

            List<Comment> findByLocation(Location location);

}
