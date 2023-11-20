DROP TABLE IF EXISTS chat;
CREATE TABLE chat
(
    id VARCHAR(255),
    created_at BIGINT NOT NULL,
    content TEXT NOT NULL,
    PRIMARY KEY (id)
);