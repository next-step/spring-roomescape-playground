CREATE TABLE time
(
    id   BIGINT NOT NULL AUTO_INCREMENT,
    time TIME   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    DATE         NOT NULL,
    time_id BIGINT,
    CONSTRAINT unique_date_and_time UNIQUE (date, time_id),
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time (id)
);
