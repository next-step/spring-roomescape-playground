CREATE TABLE reservation
(
    id       BIGINT        NOT NULL AUTO_INCREMENT,
    name     VARCHAR(255)  NOT NULL,
    datetime SMALLDATETIME NOT NULL,
    PRIMARY KEY (id)
);