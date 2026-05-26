DROP TABLE IF EXISTS reservations CASCADE;

CREATE TABLE reservations
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32),
    time TIMESTAMP
);

CREATE INDEX by_time ON reservations (time ASC);
