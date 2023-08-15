package com.example.consumer.repository;

import com.example.consumer.domain.Chat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepository {
    private final JdbcTemplate jdbcTemplate;

    public ChatRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final Chat chat) {
        jdbcTemplate.execute(
                "INSERT INTO chat (id, created_at, content) VALUES ('" + chat.getId() + "', " + chat.getCreatedAt() + ", '" + chat.getContent() + "')"
        );
    }
}
