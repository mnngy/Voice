package com.example.demo.ig.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private Long memberId;
    private String memberName;
    private String memberPassword;

    public Member(Long memberId, String memberName, String memberPassword) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
    }
}
