CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO reservation (name, date, time) VALUES ('윤성원', '2024-05-22', '10:14');
INSERT INTO reservation (name, date, time) VALUES ('윤성원', '2024-05-23', '10:15');
INSERT INTO reservation (name, date, time) VALUES ('윤성원', '2024-05-24', '10:16');