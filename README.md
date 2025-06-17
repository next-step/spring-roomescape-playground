# Spring MVC

## 기능 요구사항

### 1. 홈 화면

- [x] localhost:8080 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현
- [x] 어드민 메인 페이지는 templates/home.html 파일을 이용
- [x] Gradle에 spring-boot-starter-web 및 spring-boot-starter-thymeleaf 의존성을 추가해 html 렌더링

### 2. 예약 조회

- [x] /reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현
- [x] 어드민 메인 페이지는 templates/reservation.html 파일을 이용
- [x] 예약 페이지 요청과 예약 목록 조회 요청을 처리하는 메서드를 구현

### 3. 예약 추가/취소

- [x] API 명세를 따라 예약 추가 API 와 삭제 API를 구현

## 4. 예외 처리

- [x] 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답
- [x] 모든 예외의 경우에 400 응답을 해야하는 것은 아니기 때문에 400 응답이 필요한 경우 Exception을 정한다

### InvalidReservationRequestException

- 잘못된 예약 요청시 발생
- 다음의 경우에 발생:
    - 필수 필드 누락 (이름, 날짜, 시간)
    - 잘못된 형식의 데이터

### ReservationNotFoundException

- 존재하지 않는 예약에 대한 요청시 발생
- 예약 취소시 해당 ID의 예약이 없는 경우

