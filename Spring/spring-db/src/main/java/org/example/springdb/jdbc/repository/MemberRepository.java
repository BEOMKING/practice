package org.example.springdb.jdbc.repository;

import org.example.springdb.jdbc.domain.Member;

import java.sql.SQLException;

public interface MemberRepository {
    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    int update(String memberId, int money) throws SQLException;

    int delete(String memberId) throws SQLException;
}
