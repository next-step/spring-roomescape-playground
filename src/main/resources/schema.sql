CREATE TABLE IF NOT EXISTS reservation (
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    date    DATE NOT NULL,
    time    TIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservationTime
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time TIME NOT NULL,
    PRIMARY KEY (id)
);

