# Spring MVC 1단계

## 요구사항
- "/" 요청 시 어드민 메인 페이지가 응답한다.

## 오류해결
- Gradle 실행 오류 해결: Spring Boot 3.1.0과 호환되는 Gradle 버전 사용


# Spring MVC 2단계

## 요구사항
- GET "/reservation" 요청 시 예약 관리 페이지가 응답한다.
- GET "/reservations" 요청 시 예약 목록을 조회한다.

# Spring MVC 3단계

## 요구사항
- POST "/reservations" 요청 시 예약이 추가된다.
  - 예약 정보의 식별자는 AtomicLong을 활용한다.
  - 요청에는 식별자 id를 보내지 않는다.
- DELETE "/reservations/{id}" 요청 시 예약이 취소된다.

## 추가사항
- 요청의 JSON에는 id가 없기에 서버에서 예약 id를 추가하여 예약 객체로 변환한다.

# Spring MVC 4단계

## 요구사항
- 예약 요청에 누락된 값이 있으면 `400 Bad Request`를 반환한다.
- 날짜 또는 시간 형식이 올바르지 못하면 `400 Bad Request`를 반환한다.
- 존재하지 않는 예약을 삭제하면 `404 Not Found`를 반환한다.

