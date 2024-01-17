package com.example.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditFormDto {

    @NotBlank
    private String nick;

    public EditFormDto(){

    }

    public EditFormDto(String nick) {
        this.nick = nick;
    }
}
