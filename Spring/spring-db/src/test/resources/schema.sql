DROP TABLE member;
CREATE TABLE member
(
    member_id VARCHAR(10),
    money     INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (member_id)
);
