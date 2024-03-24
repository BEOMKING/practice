package org.example.springdb.jdbc.application;

import lombok.extern.slf4j.Slf4j;
import org.example.springdb.jdbc.domain.Member;
import org.example.springdb.jdbc.repository.MemberImprovementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.springdb.jdbc.connection.ConnectionConst.*;

@Slf4j
@SpringBootTest
class MemberImprovementServiceTest {

    @Autowired
    private MemberImprovementService memberService;

    @Autowired
    private MemberImprovementRepository memberRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }
    }

    @AfterEach
    void tearDown() {
        memberRepository.delete("from");
        memberRepository.delete("to");
        memberRepository.delete("ex");
    }

    @Test
    @DisplayName("계좌 이체")
    void accountTransfer() {
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
    void accountTransferFail() {
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

    @Test
    void AopTest() {
        log.info("memberService: {}", memberService.getClass());
        log.info("memberRepository: {}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();
    }
    
    @Test
    void duplicateKey() {
        memberRepository.delete("test");
        Member member = new Member("test", 1000);
        memberRepository.save(member);

        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(DataAccessException.class)
                .isInstanceOf(DuplicateKeyException.class);
    }
}