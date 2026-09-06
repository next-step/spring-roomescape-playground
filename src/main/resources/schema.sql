CREATE TABLE reservation (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_reservation_name_date_time UNIQUE (name, date, time)
);
