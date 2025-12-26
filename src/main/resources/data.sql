DELETE FROM reservation;
DELETE FROM time;
DELETE FROM member;
DELETE FROM theme;

ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1;
ALTER TABLE time ALTER COLUMN id RESTART WITH 1;
ALTER TABLE member ALTER COLUMN id RESTART WITH 1;
ALTER TABLE theme ALTER COLUMN id RESTART WITH 1;

INSERT INTO member (email, password, name, role) VALUES ('admin@email.com', 'password', '어드민', 'ADMIN');
INSERT INTO member (email, password, name, role) VALUES ('brown@email.com', 'password', '브라운', 'USER');

INSERT INTO time (time) VALUES ('10:00');
INSERT INTO time (time) VALUES ('11:00');
INSERT INTO time (time) VALUES ('12:00');

INSERT INTO theme (name, description, thumbnail) VALUES ('테마1', '설명1', '');
INSERT INTO theme (name, description, thumbnail) VALUES ('테마2', '설명2', '');
INSERT INTO theme (name, description, thumbnail) VALUES ('테마3', '설명3', '');

INSERT INTO reservation (name, date, time_id, theme_id) VALUES ('브라운', '2023-01-01', 1, 1);
INSERT INTO reservation (name, date, time_id, theme_id) VALUES ('브라운', '2023-01-02', 2, 1);
INSERT INTO reservation (name, date, time_id, theme_id) VALUES ('브라운', '2023-01-03', 3, 1);

