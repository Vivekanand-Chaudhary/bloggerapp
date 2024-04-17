package com.blogapp5.blogapp5.service.impl;

import com.blogapp5.blogapp5.entity.Post;
import com.blogapp5.blogapp5.exception.ResourceNotFoundException;
import com.blogapp5.blogapp5.payload.PostDto;
import com.blogapp5.blogapp5.payload.PostResponse;
import com.blogapp5.blogapp5.repository.PostRepository;
import com.blogapp5.blogapp5.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto saveOneStudentInformation(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post postResponse = postRepository.save(post);

        PostDto dto = mapToDto(postResponse);
        return dto;
    }

    @Override
    public PostResponse getAllRecord(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> contents = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getOneRecord(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("record", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public void deleteOneRecord(long id) {
        postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("record", "id", id)
        );
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updateOneRecord(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record", "id", id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post posts = postRepository.save(post);
        return mapToDto(posts);
    }

    public Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    public PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
}
