package org.example.springdb.jdbc.application;

import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.sql.SQLException;

/**
 * V2: 트랜잭션 있는 서비스
 */
@Service
public class MemberTransactionService implements MemberService {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepository memberRepository;

    public MemberTransactionService(PlatformTransactionManager transactionManager, MemberRepository memberRepository) {
        this.transactionManager = transactionManager;
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        final TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            final Member fromMember = memberRepository.findById(fromId);
            final Member toMember = memberRepository.findById(toId);

            memberRepository.update(fromId, fromMember.getMoney() - amount);
            if (toId.equals("ex")) {
                throw new IllegalStateException("이체 예외");
            }
            memberRepository.update(toId, toMember.getMoney() + amount);
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}
