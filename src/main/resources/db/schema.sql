CREATE TABLE timeslot
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    timeslot TIME   NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    roomId      VARCHAR(255) NOT NULL,
    date        DATE         NOT NULL,
    timeslot_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (timeslot_id) REFERENCES timeslot (id),

    CONSTRAINT reservation_concurrency_control UNIQUE (roomId, date, timeslot_id)
);

CREATE INDEX reservation_date_time ON reservation (date, timeslot_id);

