CREATE TABLE time
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    time       VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX unique_time_active_only ON time(time, is_deleted);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time (id)
);
