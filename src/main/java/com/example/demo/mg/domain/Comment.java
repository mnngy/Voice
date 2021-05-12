package com.example.demo.mg.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
   // private Long commentIdx;
    private String commentText;
    private String memberId;
   // private Long boardIdx;
    private String commentDate;
}