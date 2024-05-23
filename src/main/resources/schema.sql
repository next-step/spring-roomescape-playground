DROP TABLE if EXISTS reservation;
DROP TABLE if EXISTS settingTime;

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE settingTime
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);