TRUNCATE TABLE reservation RESTART IDENTITY;

INSERT INTO reservation (name, date, time)
VALUES ('브라운', '2023-01-01', '10:00');

INSERT INTO reservation (name, date, time)
VALUES ('브라운', '2023-01-02', '11:00');

INSERT INTO reservation (name, date, time)
VALUES ('브라운', '2023-01-03', '12:00');
