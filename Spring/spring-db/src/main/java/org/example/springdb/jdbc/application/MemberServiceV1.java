package org.example.springdb.jdbc.application;

import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MemberServiceV1 implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceV1(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        final Member fromMember = memberRepository.findById(fromId);
        final Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - amount);
        if (toId.equals("ex")) {
            throw new IllegalStateException("이체 예외");
        }
        memberRepository.update(toId, toMember.getMoney() + amount);
    }
}
