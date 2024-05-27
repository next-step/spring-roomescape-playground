CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id)
    );


INSERT INTO time (time) VALUES ('10:14');
INSERT INTO time (time) VALUES ('10:14');
INSERT INTO time (time) VALUES ('10:14');

INSERT INTO reservation (name, date, time_id) VALUES ('윤성원', '2024-05-22', 1);
INSERT INTO reservation (name, date, time_id) VALUES ('윤성원', '2024-05-23', 2);
INSERT INTO reservation (name, date, time_id) VALUES ('윤성원', '2024-05-24', 3);