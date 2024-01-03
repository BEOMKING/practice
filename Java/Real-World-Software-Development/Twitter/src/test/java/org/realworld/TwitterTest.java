package org.realworld;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TwitterTest {
    private final ReceiverEndPoint receiverEndPoint = mock(ReceiverEndPoint.class);

    @Test
    void 로그인_요청이_주어지면_발신자_끝점을_리턴한다() {
        // givenk
        Twitter twitter = new Twitter();

        // when
        Optional<SenderEndPoint> senderEndPoint = twitter.login(1L, "good password", receiverEndPoint);

        // then
        assertThat(senderEndPoint).isPresent();
    }

    @Test
    void 유효하지_않은_로그인_요청이_주어지면_빈_발신자_끝점을_리턴한다() {
        // given
        Twitter twitter = new Twitter();

        // when
        Optional<SenderEndPoint> senderEndPoint = twitter.login(null, "bad password", receiverEndPoint);

        // then
        assertThat(senderEndPoint).isEmpty();
    }
}
