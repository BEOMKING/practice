package org.example.springdb.jdbc.repository;

import org.example.springdb.jdbc.domain.Member;

public interface MemberImprovementRepository {
    Member save(Member member);

    Member findById(String memberId);

    int update(String memberId, int money);

    int delete(String memberId);
}
