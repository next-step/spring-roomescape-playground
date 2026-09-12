CREATE TABLE time (
    id BIGINT NOT NULL AUTO_INCREMENT,
    time TIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_time UNIQUE (time)
);

CREATE TABLE reservation (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    time_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id),
    CONSTRAINT uq_reservation_name_date_time UNIQUE (name, date, time_id)
);
