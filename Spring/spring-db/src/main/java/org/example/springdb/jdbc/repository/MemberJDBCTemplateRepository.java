package org.example.springdb.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.springdb.jdbc.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * V5: JdbcTemplate 사용한 리포지토리
 */
@Slf4j
@Repository
public class MemberJDBCTemplateRepository implements MemberImprovementRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberJDBCTemplateRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Member save(final Member member) {
        log.info("save");
        final String sql = "INSERT INTO member (member_id, money) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getMemberId(), member.getMoney());
        return member;
    }

    @Override
    public Member findById(final String memberId) {
        log.info("findById");
        final String sql = "SELECT * FROM member WHERE member_id = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(rs.getString("member_id"), rs.getInt("money"));
    }

    @Override
    public int update(final String memberId, final int money) {
        String sql = "update member set money = ? where member_id = ?";
        return jdbcTemplate.update(sql, money, memberId);
    }

    @Override
    public int delete(final String memberId) {
        return jdbcTemplate.update("delete from member where member_id = ?", memberId);
    }
}
