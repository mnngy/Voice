package com.example.demo.jh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board {
    private Long boardIdx;
    private String boardImage;
    private String boardAudio;
    private String memberIdx;
    private String boardDate;
}
