--liquibase formatted sql
-- changeset ok:1000000-1
-- comment: Initial creation of table users
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);
-- changeset ok:1000000-2
INSERT INTO users (name, password)
VALUES ('user@test.ru',
        '$2a$12$nGwpF/6/pVCIdPHJudSNcOvu8DAlCiGxRJINZsJxHPXzRfUSRr3IC');
-- changeset ok:1000000-3
ALTER TABLE users RENAME COLUMN name to username;

-- changeset ok:1000000-4
CREATE TABLE message
(
    id      BIGSERIAL PRIMARY KEY,
    text    VARCHAR NOT NULL,
    user_id BIGINT
)
-- changeset ok:1000000-5
    INSERT INTO message (text, user_id)
    VALUES
    ('message 1', '1'),
    ('message 2', '1'),
    ('message 3', '1'),
    ('message 4', '1'),
    ('message 5', '1'),
    ('message 6', '1'),
    ('message 7', '1'),
    ('message 8', '1'),
    ('message 9', '1'),
    ('message 10', '1'),
    ('message 11', '1');