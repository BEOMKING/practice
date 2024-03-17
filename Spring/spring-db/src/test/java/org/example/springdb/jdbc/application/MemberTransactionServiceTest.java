package org.example.springdb.jdbc.application;

import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.MemberJDBCDataSourceRepository;
import org.example.springdb.jdbc.repository.MemberJDBCDataSourceTransactionRepository;
import org.example.springdb.jdbc.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.springdb.jdbc.connection.ConnectionConst.*;

class MemberTransactionServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberJDBCDataSourceTransactionRepository(driverManagerDataSource);
        memberService = new MemberTransactionService(new DataSourceTransactionManager(driverManagerDataSource), memberRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.delete("from");
        memberRepository.delete("to");
        memberRepository.delete("ex");
    }

    @Test
    @DisplayName("계좌 이체")
    void accountTransfer() throws Exception {
        // given
        final String fromId = "from";
        final String toId = "to";
        final int amount = 100;

        final Member fromMember = new Member(fromId, 10000);
        final Member toMember = new Member(toId, 10000);
        memberRepository.save(fromMember);
        memberRepository.save(toMember);

        // when
        memberService.accountTransfer(fromId, toId, amount);

        // then
        final Member updatedFromMember = memberRepository.findById(fromId);
        final Member updatedToMember = memberRepository.findById(toId);
        assertThat(updatedFromMember.getMoney()).isEqualTo(9900);
        assertThat(updatedToMember.getMoney()).isEqualTo(10100);
    }

    @Test
    @DisplayName("계좌 이체 실패")
    void accountTransferFail() throws SQLException {
        // given
        final String fromId = "from";
        final String toId = "ex";
        final int amount = 100;

        final Member fromMember = new Member(fromId, 10000);
        final Member toMember = new Member(toId, 10000);
        memberRepository.save(fromMember);
        memberRepository.save(toMember);

        // when
        assertThatThrownBy(() -> memberService.accountTransfer(fromId, toId, amount))
                .isInstanceOf(IllegalStateException.class);

        // then
        final Member updatedFromMember = memberRepository.findById(fromId);
        final Member updatedToMember = memberRepository.findById(toId);
        assertThat(updatedFromMember.getMoney()).isEqualTo(10000);
        assertThat(updatedToMember.getMoney()).isEqualTo(10000);
    }

}