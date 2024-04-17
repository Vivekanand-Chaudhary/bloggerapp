package com.blogapp5.blogapp5.service;

import com.blogapp5.blogapp5.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto CommentDto);

    List<CommentDto> getAllCommentRecord(long postId);
}
