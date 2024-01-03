package helloboot.tobyspringboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class HelloJdbcRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    HelloRepository helloRepository;

    @Test
    void findHelloFailed() {
        assertThat(helloRepository.findHello("hello")).isNull();
    }

    @Test
    void increaseCountTest() {
        assertThat(helloRepository.countOf("hello")).isEqualTo(0);

        helloRepository.increaseHello("hello");
        assertThat(helloRepository.countOf("hello")).isEqualTo(1);

        helloRepository.increaseHello("hello");
        assertThat(helloRepository.countOf("hello")).isEqualTo(2);
    }
}
