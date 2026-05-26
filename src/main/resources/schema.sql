CREATE TABLE time
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    time TIME   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    datetime TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);
