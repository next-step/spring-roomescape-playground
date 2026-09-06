CREATE TABLE time
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    time TIME   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_time_time UNIQUE (time)
);

CREATE TABLE reservation
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    reserved_date DATE         NOT NULL,
    time_id       BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time (id),
    CONSTRAINT uk_reservation_reserved_date_time_id UNIQUE (reserved_date, time_id)
);
