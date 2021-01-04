package com.touryaar.service;

import com.touryaar.dto.BlogPost;
import com.touryaar.model.BlogPostModel;
import com.touryaar.respository.BlogPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;

    public ResponseEntity<String> createPost(BlogPost blogPostCreate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        BlogPostModel blogPostModel = new BlogPostModel(UUID.randomUUID().toString(),
                formatter.format(new Date()).toString(),
                blogPostCreate.getTitle(),blogPostCreate.getBody(),blogPostCreate.getImage()
        );

        blogPostRepository.save(blogPostModel);
        return new ResponseEntity<>("Post Created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<BlogPostModel>> getPosts() {
        List<BlogPostModel> blogPostModelList = blogPostRepository.findAll();

        return new ResponseEntity<>(blogPostModelList, HttpStatus.OK);
    }
}
