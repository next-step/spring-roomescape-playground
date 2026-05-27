CREATE TABLE reservations
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(32),
    time TIMESTAMP
);

CREATE INDEX reservations_by_time ON reservations (time ASC);


CREATE TABLE times
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    time TIME UNIQUE
);

CREATE INDEX times_by_time on times (time ASC);
