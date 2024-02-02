CREATE TABLE reservation
(
    id      BIGINT       NOT NULL   AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    DATE         NOT NULL,
    time    TIME         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE time
(
    id   BIGINT     NOT NULL    AUTO_INCREMENT,
    time TIME       NOT NULL    UNIQUE,
    PRIMARY KEY (id)
);
