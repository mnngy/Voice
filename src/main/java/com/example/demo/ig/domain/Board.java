package com.example.demo.ig.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {
    private Long boardIdx;
    private String boardImage;
    private String boardAudio;
    private int memberIdx;
    private String boardDate;
}
