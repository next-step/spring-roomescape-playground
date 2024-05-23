DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    reservation_date_time TIMESTAMP NOT NULL
);