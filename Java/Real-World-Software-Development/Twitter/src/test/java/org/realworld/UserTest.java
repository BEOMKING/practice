package org.realworld;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    void User_가입_요청이_들어오면_User를_저장한다() {
        // given
        final User user = new User();
        final Map<String, Object> request = Map.of(
                "id", 1L,
                "password", "password"
        );

        // when
        user.register(request);

        // then
        assertThat(1).isEqualTo(user.getCount());}
}
