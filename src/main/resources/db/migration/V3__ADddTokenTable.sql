CREATE TABLE IF NOT EXISTS shop.refresh_tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(300) NOT NULL,
    username VARCHAR(64) NOT NULL,
    CONSTRAINT FK_refresh_tokens_username_users_email
    FOREIGN KEY (username)
    REFERENCES shop.users (email)
    ON DELETE CASCADE
    ON UPDATE CASCADE);