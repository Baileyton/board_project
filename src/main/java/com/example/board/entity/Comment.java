package com.example.board.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nick;
    private Long postId;
    @NotBlank
    private String comment;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public Comment() {

    }

    public Comment(Long postId, String nick, String comment) {
        this.postId = postId;
        this.nick = nick;
        this.comment = comment;
        this.createDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }
}
