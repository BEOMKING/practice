package org.example.springdb.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.example.springdb.jdbc.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.springdb.jdbc.connection.ConnectionConst.*;

class MemberJDBCDataSourceRepositoryTest {
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        memberRepository = new MemberJDBCDataSourceRepository(dataSource);
    }

    @Test
    void saveAndFind() throws SQLException {
        // given
        final String memberId = "test";
        final int money = 1000;
        final Member member = new Member(memberId, money);

        // when
        memberRepository.save(member);

        // then
        assertThat(memberRepository.findById(memberId)).isEqualTo(member);
    }

    @Test
    void update() throws SQLException {
        // given
        final String memberId = "updated m";
        final int money = 1000;
        final Member member = new Member(memberId, money);
        memberRepository.save(member);
        final int updatedMoney = 2000;

        // when
        memberRepository.update(memberId, updatedMoney);

        // then
        assertThat(memberRepository.findById(memberId)).isEqualTo(new Member(memberId, updatedMoney));
    }

    @Test
    void delete() throws SQLException {
        // given
        final String memberId = "delete m";
        final int money = 1000;
        final Member member = new Member(memberId, money);
        memberRepository.save(member);

        // when
        memberRepository.delete(memberId);

        // then
        assertThatThrownBy(() -> memberRepository.findById(memberId))
                .isExactlyInstanceOf(NoSuchElementException.class);
    }
}