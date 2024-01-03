package helloboot.tobyspringboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HelloApiTest {

    @Test
    void helloApi() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();

        ResponseEntity<String> result = testRestTemplate.getForEntity("http://localhost:8080/bjp/hello?name={name}", String.class, "Spring");

        assertAll(() -> {
            assertEquals("*Hello, Spring*", result.getBody());
            assertEquals(200, result.getStatusCodeValue());
        });
    }
}
