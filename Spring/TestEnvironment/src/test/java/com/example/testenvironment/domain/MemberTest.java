package com.example.testenvironment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberTest extends SpringTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void test() {
        Member member = new Member("BJP");
        memberRepository.save(member);

        Assertions.assertThat(memberRepository.findAll()).hasSize(1);
    }
}
