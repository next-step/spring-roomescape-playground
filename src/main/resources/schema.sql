DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS time;

CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT, //id 컬럼
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id) //id를 primary key로 사용
);
//예약 테이블이 시간 테이블을 참조하도록
CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT, //time 테이블의 id를 저장
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id)
);
