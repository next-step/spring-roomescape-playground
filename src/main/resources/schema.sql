DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS time;

CREATE TABLE time (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      time VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id)
);

CREATE TABLE reservation (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             name VARCHAR(255) NOT NULL,
                             date VARCHAR(255) NOT NULL,
                             time_id BIGINT,
                             PRIMARY KEY (id),
                             FOREIGN KEY (time_id) REFERENCES time(id)
);

CREATE TABLE member
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);


INSERT INTO member (name, email, password, role)
VALUES ('어드민', 'admin@email.com', 'password', 'ADMIN');
