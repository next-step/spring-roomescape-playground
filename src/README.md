# 방탈출 예약 프로그램

---

## step 1 홈화면

- localhost:8080 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현

## step 2 예약 조회

- /reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현
- /reservations 요청 시 예약 목록을 JSON 데이터로 조회할 수 있도록 구현


## step 3 예약 추가 / 취소

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현
- 화면에서 예약 추가와 취소가 잘 동작해야 함

## step 4 예외 처리

- 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답
  예를 들면 예약 추가 시 필요한 인자값이 비어있는 경우 혹은 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우가 있다


---

## 프로젝트 구조

### Reservation


- 예약 정보를 표현하는 도메인 객체
- 생성 시 이름/날짜/시간 검증을 수행하여 유효한 예약만 생성되도록 보장


### ReservationController

- /reservation 요청 시 reservation.html 응답
- /reservations 요청 시 예약 목록 조회 API 응답(JSON)
- /reservations POST 요청에서 예약 추가
- /reservations/{id} DELETE 요청에서 예약 삭제


### RoomescapeApplication

- Entry Point

### RoomescapeController

- / 요청 시 home.html 응답


### ReservationRequest

- 예약 생성 요청을 받을 때 사용하는 DTO

### GlobalExceptionHandler

- 예약 생성/삭제 시 발생하는 예외를 처리
- 잘못된 요청에 대해 400 응답 반환

---



