create table reservations
(
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    reserve_date  DATE         NOT NULL,
    reserve_time  TIME         NOT NULL,
    PRIMARY KEY (id)
);