package com.example.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordEditFormDto {
    @NotBlank
    private String currentPassword;

    @NotBlank
    private String editPassword;

    @NotBlank
    private String editPasswordCheck;
}
