-- 기존 데이터 삭제 후 삽입
DELETE FROM reservation;

-- AUTO_INCREMENT 초기화
ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1;

-- 초기 데이터 삽입
INSERT INTO reservation (name, date, time) VALUES ('브라운', '2023-01-01', '10:00');
INSERT INTO reservation (name, date, time) VALUES ('브라운', '2023-01-02', '11:00');
INSERT INTO reservation (name, date, time) VALUES ('브라운', '2023-01-03', '12:00');
