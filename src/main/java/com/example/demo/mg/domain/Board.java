package com.example.demo.mg.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board {
    private Long boardIdx;
    private String memberId;
    private String boardImage;
    private String boardAudio;
    private String boardDate;
    private Long likecount;
}
