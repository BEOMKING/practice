package org.example.springdb.jdbc.repository;

import org.example.springdb.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class MemberJDBCRepositoryTest {
    private MemberJDBCRepository memberJDBCRepository = new MemberJDBCRepository();

    @Test
    void save() throws SQLException {
        // given
        final String memberId = "test";
        final int money = 1000;

        // when
        memberJDBCRepository.save(new Member(memberId, money));

        // then
    }
}