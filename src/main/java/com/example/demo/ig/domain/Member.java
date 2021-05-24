package com.example.demo.ig.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
    private Long memberIdx;
    private String memberImage;
    private String memberId;
    private String memberPassword;
    private int memberGrade;
}
