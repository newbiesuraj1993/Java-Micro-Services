package com.infy.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.student.model.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

}
