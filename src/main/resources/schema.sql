CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    DATE NOT NULL,
    time    VARCHAR(5) NOT NULL,
    PRIMARY KEY (id)
);
