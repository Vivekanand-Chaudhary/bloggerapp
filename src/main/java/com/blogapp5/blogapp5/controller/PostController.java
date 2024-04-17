package com.blogapp5.blogapp5.controller;

import com.blogapp5.blogapp5.payload.PostDto;
import com.blogapp5.blogapp5.payload.PostResponse;
import com.blogapp5.blogapp5.service.PostService;
import com.blogapp5.blogapp5.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPostRecord(@RequestBody PostDto postDto){
       return new ResponseEntity<>(postService.saveOneStudentInformation(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllRecord(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return postService.getAllRecord(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public PostDto getOneRecord(@PathVariable("id") long id){
        return postService.getOneRecord(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeOneRecord(@PathVariable("id") long id){
        postService.deleteOneRecord(id);
        return new ResponseEntity<String>("Record is Deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOneRecord(@RequestBody PostDto postDto, @PathVariable("id") long id){
        postService.updateOneRecord(id, postDto);
       return new ResponseEntity<>("Record Is saved",HttpStatus.CREATED);
    }
}

