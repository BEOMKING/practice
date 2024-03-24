package org.example.springdb.jdbc.application;

import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.MemberRepository;

import java.sql.SQLException;

/**
 * V1: 트랜잭션 없는 서비스
 */
public class MemberNoTransactionService implements MemberService {

    private final MemberRepository memberRepository;

    public MemberNoTransactionService(MemberRepository memberRepository) {
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
