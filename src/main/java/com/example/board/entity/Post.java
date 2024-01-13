package com.example.board.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@EqualsAndHashCode(exclude = {"createDate","modifiedDate"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Long views;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.views = 0L;
    }

    public Post(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.views = 0L;
    }

    public Post() {

    }

    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }
}
