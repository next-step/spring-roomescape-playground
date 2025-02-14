CREATE TABLE IF NOT EXISTS reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    customer_name    VARCHAR(255) NOT NULL,
    reservation_date    VARCHAR(255) NOT NULL,
    reservation_time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
