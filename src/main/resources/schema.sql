CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO reservation (name, date, time) VALUES
                                               ('Alice', '2025-03-01', '10:00:00'),
                                               ('Bob', '2025-03-01', '11:30:00'),
                                               ('Charlie', '2025-03-02', '14:15:00'),
                                               ('David', '2025-03-03', '09:45:00'),
                                               ('Emma', '2025-03-04', '16:20:00'),
                                               ('Frank', '2025-03-05', '18:00:00'),
                                               ('Grace', '2025-03-06', '20:30:00'),
                                               ('Hannah', '2025-03-07', '12:00:00'),
                                               ('Ian', '2025-03-08', '13:50:00'),
                                               ('Jack', '2025-03-09', '17:10:00');


SELECT * FROM reservation;
