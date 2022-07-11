package com.bjp.objects.ticket_sell;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("team")
public class Team {

    private final String name;
    private final List<Member> members;

    @Getter
    @RequiredArgsConstructor
    static class Member {

        private final String name;
        private final Integer age;

    }

}
