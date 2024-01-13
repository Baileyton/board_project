package com.example.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostHtmlDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Long views;
}
