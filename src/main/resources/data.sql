DELETE FROM reservation;
DELETE FROM time;
DELETE FROM member;

ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1;
ALTER TABLE time ALTER COLUMN id RESTART WITH 1;
ALTER TABLE member ALTER COLUMN id RESTART WITH 1;

INSERT INTO time (time) VALUES ('10:00');
INSERT INTO time (time) VALUES ('11:00');
INSERT INTO time (time) VALUES ('12:00');

INSERT INTO reservation (name, date, time_id) VALUES ('브라운', '2023-01-01', 1);
INSERT INTO reservation (name, date, time_id) VALUES ('브라운', '2023-01-02', 2);
INSERT INTO reservation (name, date, time_id) VALUES ('브라운', '2023-01-03', 3);

INSERT INTO member (email, password, name, role) VALUES ('admin@email.com', 'password', '어드민', 'ADMIN');
INSERT INTO member (email, password, name, role) VALUES ('brown@email.com', 'password', '브라운', 'USER');
