CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    room_id VARCHAR(255) NOT NULL,
    date    DATE         NOT NULL,
    time    TIME         NOT NULL,
    PRIMARY KEY (id),

    CONSTRAINT reservation_concurrency_control UNIQUE (room_id, date, time)
);

CREATE INDEX reservation_date_time ON reservation (date, time);