CREATE TABLE time (
    id BIGINT NOT NULL AUTO_INCREMENT,
    time VARCHAR(8) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    date VARCHAR(10) NOT NULL,
    time_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id)
);

INSERT INTO time (time) VALUES ('10:00');
INSERT INTO time (time) VALUES ('13:00');
INSERT INTO time (time) VALUES ('17:00');

