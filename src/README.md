# 방탈출 예약 프로그램

---

## step 1 홈화면

- localhost:8080 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현

## step 2 예약 조회

- /reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현
- /reservations 요청 시 예약 목록을 JSON 데이터로 조회할 수 있도록 구현

---

## 프로젝트 구조

### Reservation

- 예약 조회 테스트를 위한 샘플 예약 데이터 객체

### ReservationController

- /reservation 요청 시 reservation.html 응답
- /reservations 요청 시 예약 모록 조회 API 응다(JSON)

### RoomescapeApplication

- Entry Point

### RoomescapeController

- / 요청 시 home.html 응답

---

## 프로그램 실행 과정

1. 사용자가 localhost:8080/ 접근 → RoomescapeController → home.html 렌더링
2. 관리자가 /reservation 접근 → ReservationController → reservation.html 렌더링
3. reservation 페이지는 내부에서 /reservations API 호출 → JSON 목록 조회



