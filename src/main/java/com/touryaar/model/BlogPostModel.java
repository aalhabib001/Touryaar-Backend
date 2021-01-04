package com.touryaar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "blog_post")
public class BlogPostModel {
    @Id
    private String id;

    private String creationTime;

    private String title;

    private String body;

    private String image;

}
