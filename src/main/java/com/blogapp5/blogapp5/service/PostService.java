package com.blogapp5.blogapp5.service;

import com.blogapp5.blogapp5.payload.PostDto;
import com.blogapp5.blogapp5.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto saveOneStudentInformation(PostDto postDto);

    PostResponse getAllRecord(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getOneRecord(long id);

    void deleteOneRecord(long id);

    PostDto updateOneRecord(long id, PostDto postDto);
}
