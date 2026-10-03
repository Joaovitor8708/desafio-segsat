CREATE TABLE posts (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title TEXT NOT NULL,
    body TEXT NOT NULL
);