package helloboot.tobyspringboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class JdbcTemplateTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void insertAndQuery() {
        jdbcTemplate.update("INSERT INTO MEMBER VALUES(?, ?)", "hello", 1);
        jdbcTemplate.update("INSERT INTO MEMBER VALUES(?, ?)", "world", 2);

        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MEMBER", Long.class);
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    void insertAndQuery2() {
        jdbcTemplate.update("INSERT INTO MEMBER VALUES(?, ?)", "hello", 1);
        jdbcTemplate.update("INSERT INTO MEMBER VALUES(?, ?)", "world", 2);

        Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MEMBER", Long.class);
        Assertions.assertThat(count).isEqualTo(2);
    }
}
