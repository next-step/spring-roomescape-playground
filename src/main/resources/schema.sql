CREATE TABLE Reservation
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date DATE         NOT NULL,
    time TIME         NOT NULL
);