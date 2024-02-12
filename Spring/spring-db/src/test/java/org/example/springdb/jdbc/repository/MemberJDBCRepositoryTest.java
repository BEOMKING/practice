package org.example.springdb.jdbc.repository;

import org.example.springdb.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class MemberJDBCRepositoryTest {
    private MemberJDBCRepository memberJDBCRepository = new MemberJDBCRepository();

    @Test
    void saveAndFind() throws SQLException {
        // given
        final String memberId = "test";
        final int money = 1000;
        final Member member = new Member(memberId, money);

        // when
        memberJDBCRepository.save(member);

        // then
        assertThat(memberJDBCRepository.findById(memberId)).isEqualTo(member);
    }

    @Test
    void update() throws SQLException {
        // given
        final String memberId = "updated m";
        final int money = 1000;
        final Member member = new Member(memberId, money);
        memberJDBCRepository.save(member);
        final int updatedMoney = 2000;

        // when
        memberJDBCRepository.update(memberId, updatedMoney);

        // then
        assertThat(memberJDBCRepository.findById(memberId)).isEqualTo(new Member(memberId, updatedMoney));
    }

}