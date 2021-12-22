CREATE TABLE IF NOT EXISTS users_telegram
(
    id    BIGSERIAL PRIMARY KEY ,
    user_id VARCHAR(30) NOT NULL ,
    telegram_chat_id VARCHAR(30) NOT NULL unique
);
CREATE TABLE IF NOT EXISTS users
(
    id    BIGSERIAL PRIMARY KEY ,
    login VARCHAR(30) not null unique ,
    password VARCHAR(30) not null
);