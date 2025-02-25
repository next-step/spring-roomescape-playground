CREATE TABLE IF NOT EXISTS reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(30) NOT NULL,
    `date`    DATE NOT NULL,
    `time`    TIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);
