CREATE TABLE reservations
(
    id                  BIGINT       GENERATED ALWAYS AS IDENTITY,
    name                VARCHAR(255) NOT NULL,
    reservation_date    DATE         NOT NULL,
    reservation_time    TIME         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_reservations_date_time UNIQUE (reservation_date, reservation_time)
);
