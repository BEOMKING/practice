package com.study.applicationtest.member;

import com.study.applicationtest.domain.Member;
import com.study.applicationtest.domain.Study;
import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
