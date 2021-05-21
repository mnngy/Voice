package com.example.demo.ig.domain;

import lombok.*;

import java.util.Date;

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
    private Date boardDate;
}
