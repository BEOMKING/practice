package org.example.springdb.jdbc.application;

import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.MemberImprovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * V4: 트랜잭션 AOP을 사용한 서비스
 */
@Service
public class MemberImprovementService {

    private final MemberImprovementRepository memberRepository;

    public MemberImprovementService(final MemberImprovementRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int amount) {
        final Member fromMember = memberRepository.findById(fromId);
        final Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - amount);
        if (toId.equals("ex")) {
            throw new IllegalStateException("이체 예외");
        }
        memberRepository.update(toId, toMember.getMoney() + amount);
    }
}
