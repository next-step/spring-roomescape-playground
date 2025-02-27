create table times
(
    time_id BIGINT NOT NULL AUTO_INCREMENT,
    available_time    TIME   NOT NULL,
    PRIMARY KEY (time_id)
);

create table reservations
(
    reservation_id BIGINT       NOT NULL AUTO_INCREMENT,
    name           VARCHAR(255) NOT NULL,
    reserved_date  DATE         NOT NULL,
    time_id        BIGINT,
    PRIMARY KEY (reservation_id),
    FOREIGN KEY (time_id) REFERENCES times(time_id)
);
