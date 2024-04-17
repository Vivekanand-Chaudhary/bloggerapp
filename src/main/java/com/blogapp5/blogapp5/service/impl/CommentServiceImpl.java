package com.blogapp5.blogapp5.service.impl;

import com.blogapp5.blogapp5.entity.Comment;
import com.blogapp5.blogapp5.entity.Post;
import com.blogapp5.blogapp5.exception.ResourceNotFoundException;
import com.blogapp5.blogapp5.payload.CommentDto;
import com.blogapp5.blogapp5.repository.CommentRepository;
import com.blogapp5.blogapp5.repository.PostRepository;
import com.blogapp5.blogapp5.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Record", "id", postId)
        );

        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        Comment NewComment = commentRepository.save(comment);
        return mapToDto(NewComment);
    }

    @Override
    public List<CommentDto> getAllCommentRecord(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    public Comment mapToComment(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    public CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
