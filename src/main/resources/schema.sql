create table reservations
(
    reservation_id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    reserved_date  DATE         NOT NULL,
    reserved_time  TIME         NOT NULL,
    PRIMARY KEY (reservation_id)
);
