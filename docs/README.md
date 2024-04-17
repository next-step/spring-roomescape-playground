
## 5단계
- 데이터베이스 의존성 추가
- schema.sql 생성
- application.properties에 database 정보 추가

## 6단계
- ReservationDAO 생성
- ReservationDAO에 데이터 조회를 위한 기능 생성
- ReservationService에서 ReservationDAO에 접근하는 기능 생성
- ReservationService에 ReservationDAO에서 전달받은 Reservation 리스트를 ReservationDAO 리스트로 바꿔주는 기능 생성
- ReservationController에 ReservationService를 호출하여 데이터 조회하는 기능 생성

## 7단계
- ReservationDAO에 데이터베이스에 데이터 추가하는 기능 생성
- ReservationDAO에 id 값 받아서 해당 id 데이터 삭제하는 기능 생성
- ReservationService에 ReservationDAO 호출하여 데이터 추가하는 기능 생성
- ReservationService에 ReservationDAO 호출하여 데이터 삭제하는 기능 생성
- ReservationController에 클라이언트로부터 id 값 받아서 ReservationService 호출하여 데이터 추가하는 기능 생성
- ReservationController에 클라이언트로부터 id 값 받아서 ReservationService 호출하여 데이터 삭제하는 기능 생성