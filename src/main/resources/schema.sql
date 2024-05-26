CREATE TABLE times
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time TIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservations
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES times(id)
);
