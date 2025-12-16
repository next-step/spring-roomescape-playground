CREATE TABLE IF NOT EXISTS reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    DATE         NOT NULL,
    time    Time         NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    );
