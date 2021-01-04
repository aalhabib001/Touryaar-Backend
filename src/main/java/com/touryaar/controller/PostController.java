package com.touryaar.controller;

import com.touryaar.dto.BlogPost;
import com.touryaar.model.BlogPostModel;
import com.touryaar.service.BlogPostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/api/blog")
public class PostController {
    private final BlogPostService blogPostService;

    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody BlogPost blogPostCreate){
        return blogPostService.createPost(blogPostCreate);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<BlogPostModel>> getPosts(){
        return blogPostService.getPosts();
    }
}
