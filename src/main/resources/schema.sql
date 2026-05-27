CREATE TABLE time
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    time TIME UNIQUE
);

CREATE INDEX times_by_time on time (time ASC);


CREATE TABLE reservation
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32),
    date DATE,
    time_id BIGINT,

    FOREIGN KEY (time_id) REFERENCES time(id),
    UNIQUE (date, time_id)
);

CREATE INDEX reservations_by_timestamp ON reservation (date, time_id);
