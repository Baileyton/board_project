package com.example.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostFormDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
