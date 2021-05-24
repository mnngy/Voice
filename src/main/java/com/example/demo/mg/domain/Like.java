package com.example.demo.mg.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Like {
    private Long boardIdx;
    private Long likecount;
}
