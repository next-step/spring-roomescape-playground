CREATE TABLE reservation
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)    NOT NULL,
    reserved_at TIMESTAMP       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_reservation_reserved_at UNIQUE (reserved_at)
);

CREATE TABLE time
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    time TIME   NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_time_time UNIQUE (time)
);
