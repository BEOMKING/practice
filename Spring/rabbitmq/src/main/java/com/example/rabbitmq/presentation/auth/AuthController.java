package com.example.rabbitmq.presentation.auth;

import com.example.rabbitmq.domain.auth.ResourcePathRequest;
import com.example.rabbitmq.domain.auth.TopicPathRequest;
import com.example.rabbitmq.domain.auth.UserPathRequest;
import com.example.rabbitmq.domain.auth.VhostPathRequest;
import com.example.rabbitmq.infra.config.RabbitmqConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rabbit/auth")
public class AuthController {
    private static final String VHOST = "chat";

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @GetMapping
    public String index() {
        return "ok";
    }

    @PostMapping(path = "/user", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postUser(UserPathRequest request) {
        if (request.username().startsWith(username) && password.equals(request.password())) {
            return "allow administrator";
        } else {
            return "deny";
        }
    }

    @PostMapping("/vhost")
    public String postVhost(VhostPathRequest request) {
        if (request.username().startsWith(username) && VHOST.equals(request.vhost())) {
            return "allow";
        } else {
            return "deny";
        }
    }

    @PostMapping("/resource")
    public String postResource(ResourcePathRequest request) {
        if (request.username().startsWith("user") && "chat".equals(request.vhost())) {
            if ("exchange".equals(request.resource())) {
                if ("request".equals(request.name())
                        && List.of("configure", "write").contains(request.permission())) {
                    return "allow";
                } else if ("user".equals(request.name())
                        && Stream.of("read").anyMatch(request.permission()::equals)) {
                    return "allow";
                } else if ("amq.default".equals(request.name())) {
                    return "allow";
                }
            } else if ("queue".equals(request.resource()) && ("user." + request.username()).equals(request.name())
                    && List.of("configure", "write", "read").contains(request.permission())) {
                return "allow";
            }
        }

        return "deny";
    }

    @PostMapping("/topic")
    public String postTopic(TopicPathRequest request) {
        Pattern pattern = Pattern.compile("^(chat|command)\\.\\w+");
        // 패턴 :: 문자열이 chat.* 과 일치하는지 확인

        if (request.username().startsWith(username)
                && VHOST.equals(request.vhost())
                && "topic".equals(request.resource())) {
            if (RabbitmqConfig.REQUEST_EXCHANGE.equals(request.name())
                    && "write".equals(request.permission())
                    && (request.routingKey() == null || pattern.matcher(request.routingKey()).find())) {
                return "allow";
            } else if (username.equals(request.name()) && "read".equals(request.permission())
                    && (request.routingKey() == null || ("*.user." + request.username()).equals(request.routingKey()))) {
                return "allow";
            }
        }

        return "deny";
    }

}