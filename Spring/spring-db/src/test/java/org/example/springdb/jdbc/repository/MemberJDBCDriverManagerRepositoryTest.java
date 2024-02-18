package org.example.springdb.jdbc.repository;

import org.example.springdb.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberJDBCDriverManagerRepositoryTest {
    private MemberJDBCDriverManagerRepository memberJDBCDriverManagerRepository = new MemberJDBCDriverManagerRepository();

    @Test
    void saveAndFind() throws SQLException {
        // given
        final String memberId = "test";
        final int money = 1000;
        final Member member = new Member(memberId, money);

        // when
        memberJDBCDriverManagerRepository.save(member);

        // then
        assertThat(memberJDBCDriverManagerRepository.findById(memberId)).isEqualTo(member);
    }

    @Test
    void update() throws SQLException {
        // given
        final String memberId = "updated m";
        final int money = 1000;
        final Member member = new Member(memberId, money);
        memberJDBCDriverManagerRepository.save(member);
        final int updatedMoney = 2000;

        // when
        memberJDBCDriverManagerRepository.update(memberId, updatedMoney);

        // then
        assertThat(memberJDBCDriverManagerRepository.findById(memberId)).isEqualTo(new Member(memberId, updatedMoney));
    }

    @Test
    void delete() throws SQLException {
        // given
        final String memberId = "delete m";
        final int money = 1000;
        final Member member = new Member(memberId, money);
        memberJDBCDriverManagerRepository.save(member);

        // when
        memberJDBCDriverManagerRepository.delete(memberId);

        // then
        assertThatThrownBy(() -> memberJDBCDriverManagerRepository.findById(memberId))
                .isExactlyInstanceOf(NoSuchElementException.class);
    }
}