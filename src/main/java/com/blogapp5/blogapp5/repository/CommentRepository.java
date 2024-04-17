package com.blogapp5.blogapp5.repository;

import com.blogapp5.blogapp5.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPostId(long postId);

}
