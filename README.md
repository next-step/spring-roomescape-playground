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
  - 예약 정보의 식별자는 AtomicLong을 활용한다.[x]
    - 데이터베이스 적용 후에는 DB가 생성한 id를 사용하도록 변경
  - 요청에는 식별자 id를 보내지 않는다.
- DELETE "/reservations/{id}" 요청 시 예약이 취소된다.

## 추가사항
- 요청의 JSON에는 id가 없기에 서버에서 예약 id를 추가하여 예약 객체로 변환한다.

# Spring MVC 4단계

## 요구사항
- 예약 요청에 누락된 값이 있으면 `400 Bad Request`를 반환한다.
- 예약자 이름이 20자를 초과하면 `400 Bad Request`를 반환한다.
- 날짜 또는 시간 형식이 올바르지 못하면 `400 Bad Request`를 반환한다.
- 존재하지 않는 예약을 삭제하면 `404 Not Found`를 반환한다.



# Spring JDBC 5~7단계

## 요구사항
- H2 데이터베이스를 적용한다.
- 예약 조회/추가/취소 API가 데이터베이스를 사용하도록 변경한다.
- 기존의 `List`, `AtomicLong`을 제거한다.

## 적용 사항
- `spring-boot-starter-jdbc`, H2 의존성을 추가하고 `schema.sql`로 reservation 테이블을 생성했다.
- `jdbc:h2:mem:database`를 사용해 데이터베이스를 설정하고 H2 Console을 활성화했다.
- `JdbcTemplate`과 `RowMapper`를 사용해 데이터베이스의 예약 정보를 조회하도록 변경했다.
- 예약 추가 시 `KeyHolder`로 DB가 생성한 id를 받아 `Location` 헤더와 응답에 사용했다.
- 예약 삭제 시 `JdbcTemplate.update()`의 반환값으로 삭제 여부를 확인하도록 변경했다.
- 존재하지 않는 예약의 조회·삭제 요청은 `404 Not Found`를 반환하도록 처리했다.
- 동일한 날짜와 시간에는 중복 예약할 수 없도록 DB 유니크 제약을 추가했다.
- 현재 이후의 날짜와 시간에만 예약할 수 있도록 예약 시간을 검증했다.

# Spring CORE 8~10단계

## 요구사항
- GET "/time" 요청 시 시간 관리 페이지가 응답한다.
- 시간 관리 기능을 추가한다.
  - DB 스키마에 time 테이블을 추가하고, 시간 조회/추가/삭제 API를 작성한다.

## 적용 사항
- `time` 테이블과 `Time` domain/DTO/Controller/Repository를 추가했다.
- 존재하지 않는 시간의 삭제 요청은 `404 Not Found`를 반환하도록 처리했다.
- 동일한 시간이 중복 저장되지 않도록 유니크 제약을 추가했다.