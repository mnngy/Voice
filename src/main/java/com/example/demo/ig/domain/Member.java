package com.example.demo.ig.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Member {
    private Long memberIdx;
    private String memberImage;
    private String memberId;
    private String memberPassword;
//    private int memberGrade;
}
