CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    date    DATE NOT NULL,
    time    TIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_reservation_date_time UNIQUE (date, time)
);

CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(5) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_time_value UNIQUE (time)
);
