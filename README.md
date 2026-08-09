# 방 탈출 예약 관리 

## 기능 요구사항

### 1단계 - 홈 화면
- [x] '/' 요청 시 홈 화면('home.html')을 응답한다.

### 2단계 - 예약 조회
- [x] '/reservation' 요청 시 예약 관리 페이지('reservation.html')를 응답한다.
- [x] '/reservations' 요청 시 예약 목록을 JSON으로 응답한다.

## 테스트 목록

### MissionStepTest
- [x] `/` 요청 시 200을 응답한다.
- [x] `/reservation` 요청 시 200을 응답한다.
- [x] `/reservations` 요청 시 200과 예약 목록(3개)을 응답한다.

### ReservationTest
- [x] 이름이 정상이면 예약이 생성된다.
- [x] 이름이 null이면 예외가 발생한다.
- [x] 이름이 공백이면 예외가 발생한다.
