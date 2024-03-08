CREATE TABLE time
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    time_value VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time (id)
);