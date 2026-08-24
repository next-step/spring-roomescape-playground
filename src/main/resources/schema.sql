CREATE TABLE reservation
(
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)    NOT NULL,
    reserved_at TIMESTAMP       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_reservation_reserved_at UNIQUE (reserved_at)
);
