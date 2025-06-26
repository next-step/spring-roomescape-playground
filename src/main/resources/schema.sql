CREATE TABLE time (
                      id   BIGINT       NOT NULL AUTO_INCREMENT,
                      time TIME         NOT NULL,
                      PRIMARY KEY (id)
);

CREATE TABLE reservation (
                             id      BIGINT       NOT NULL AUTO_INCREMENT,
                             name    VARCHAR(30)  NOT NULL,
                             date    DATE         NOT NULL,
                             time_id BIGINT       NOT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (time_id) REFERENCES time(id)
);

CREATE TABLE member (
                        id       BIGINT       NOT NULL AUTO_INCREMENT,
                        email    VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(60)  NOT NULL,
                        name     VARCHAR(50)  NOT NULL,
                        role     VARCHAR(10)  NOT NULL,
                        PRIMARY KEY (id)
);

INSERT INTO member (email, password, name, role) VALUES ('admin@email.com', 'password', '어드민', 'ADMIN');
