CREATE TABLE RESERVATION
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    date VARCHAR(10) NOT NULL, -- yyyy-mm-dd -> 10 글자
    time VARCHAR(5)  NOT NULL -- hh-mm -> 5 글자
);
