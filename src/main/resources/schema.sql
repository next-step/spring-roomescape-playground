-- 테이블이 이미 존재하면 삭제
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS time;

-- time 테이블 생성
CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- reservation 테이블 생성
CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id) ON DELETE CASCADE ON UPDATE CASCADE
);
