INSERT INTO timeslot (timeslot)
VALUES ('10:00');
INSERT INTO timeslot (timeslot)
VALUES ('12:00');
INSERT INTO timeslot (timeslot)
VALUES ('14:00');

INSERT INTO reservation (name, roomId, date, timeslot_id)
VALUES ('홍길동', '1호', '2027-05-14', 1);
INSERT INTO reservation (name, roomId, date, timeslot_id)
VALUES ('김철수', '2호', '2027-05-14', 2);
INSERT INTO reservation (name, roomId, date, timeslot_id)
VALUES ('김영희', '3호', '2027-05-15', 3);
