CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time TIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_time_value UNIQUE (time)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    date    DATE NOT NULL,
    time_id    BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id),
    CONSTRAINT uk_reservation_date_time UNIQUE (date, time_id)
);
