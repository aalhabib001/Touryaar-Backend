package com.touryaar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogPost {
    private String creationTime;

    private String title;

    private String body;

    private String image;
}
