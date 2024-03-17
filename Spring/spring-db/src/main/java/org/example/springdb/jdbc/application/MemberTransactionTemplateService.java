package org.example.springdb.jdbc.application;

import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.MemberRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * V3: 트랜잭션 템플릿을 사용한 서비스
 */
public class MemberTransactionTemplateService implements MemberService {

    private final TransactionTemplate transactionTemplate;
    private final MemberRepository memberRepository;

    public MemberTransactionTemplateService(final PlatformTransactionManager transactionManager, final MemberRepository memberRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            try {
                final Member fromMember = memberRepository.findById(fromId);
                final Member toMember = memberRepository.findById(toId);

                memberRepository.update(fromId, fromMember.getMoney() - amount);
                if (toId.equals("ex")) {
                    throw new IllegalStateException("이체 예외");
                }
                memberRepository.update(toId, toMember.getMoney() + amount);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });
    }
}
