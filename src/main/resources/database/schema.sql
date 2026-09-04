CREATE TABLE times
(
    id                  BIGINT       GENERATED ALWAYS AS IDENTITY,
    start_at            TIME         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_times_start_at UNIQUE (start_at)
);

CREATE TABLE reservations
(
    id                  BIGINT       GENERATED ALWAYS AS IDENTITY,
    name                VARCHAR(255) NOT NULL,
    reservation_date    DATE         NOT NULL,
    time_id             BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES times(id),
    CONSTRAINT uk_reservations_date_time UNIQUE (reservation_date, time_id)
);
