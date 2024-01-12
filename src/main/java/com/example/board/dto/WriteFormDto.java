package com.example.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WriteFormDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
