CREATE TABLE reservation
(
    id                    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name                  VARCHAR(255) NOT NULL,
    reservation_date_time TIMESTAMP    NOT NULL
);

CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
