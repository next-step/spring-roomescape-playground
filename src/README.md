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

## step 5 데이터베이스 적용하기

- h2 데이터베이스를 활용하여 데이터를 저장하도록 수정

## step 6

- 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용

## step 7

- 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정
    - 기존에 사용하던 List 및 AtomicLong 을 제거
- 예약 관리 기능이 정상 동작하도록 기능을 완성

---

## 프로젝트 구조

### Reservation

- 예약 정보를 표현하는 도메인 객체
- 생성 시 이름/날짜/시간 검증을 수행하여 유효한 예약만 생성되도록 보장
- DB에서 조회한 예약은 newReservationFromDb 로 생성
- 신규 예약은 createReservation 로 생성하여 검증 포함

### ReservationController

- /reservations 요청 시 예약 목록 조회 API 응답(JSON)
- /reservations POST 요청에서 예약 추가
- /reservations/{id} DELETE 요청에서 예약 삭제

### RoomescapeApplication

- Entry Point

### PageController

- / 요청 시 home.html 응답
- /reservation 요청시 reservation.html 응답

### Reservation Service

- 도메인 객체 생성
- DAO를 통해 예약 추가·조회·삭제 처리
- 삭제 시 영향받은 row 수를 확인하여, 없으면 NotFoundReservationException 발생

### RerservationDao

- KeyHolder 를 사용해 예약 추가 시 생성된 ID 반환
- 전체 예약 조회/삭제 기능 구현

### ReservationRequest

- 예약 생성 요청을 받을 때 사용하는 DTO
- name/date/time 값에 대해 NotBlank 검증 수행

### ReservationResponse

- Reservation 도메인을 응답 형태로 변환하는 DTO

### GlobalExceptionHandler

- 예약 생성/삭제 시 발생하는 예외를 처리
- 잘못된 요청에 대해 400 응답 반환
- DB 관련 예외(DataAccessException)는 500으로 내부 서버 오류 처리

---



