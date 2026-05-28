DROP TABLE IF EXISTS reservation;
//기존에 reservation 테이블이 있으면 삭제

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id) //id를 기본키로 지정
);
