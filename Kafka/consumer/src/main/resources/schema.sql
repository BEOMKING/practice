CREATE TABLE IF NOT EXISTS chat
(
    id VARCHAR(255),
    created_at BIGINT NOT NULL,
    content TEXT NOT NULL,
    PRIMARY KEY (id)
);