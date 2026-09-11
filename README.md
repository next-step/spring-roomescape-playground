# spring-roomescape-playground

## 클래스별 구현 기능 목록

---

## `PageController`

### 예약 페이지 응답 기능

* [x] `@Controller`를 이용해 View 응답을 처리한다.
* [x] `GET /reservation` 요청을 처리한다.
* [x] `@GetMapping("/reservation")`을 이용해 예약 페이지 요청을 매핑한다.
* [x] `"new-reservation"`을 반환한다.
* [x] Thymeleaf를 통해 `templates/new-reservation.html`을 응답한다.

### 시간 관리 페이지 응답 기능

* [x] `GET /time` 요청을 처리한다.
* [x] `@GetMapping("/time")`을 이용해 시간 관리 페이지 요청을 매핑한다.
* [x] `"time"`을 반환한다.
* [x] Thymeleaf를 통해 `templates/time.html`을 응답한다.

---

## `ReservationController`

### 예약 API 관리 기능

* [x] 예약 관련 HTTP 요청과 응답을 처리한다.
* [x] `ReservationService`를 생성자 주입을 통해 전달받는다.
* [x] API 응답을 담당하기 위해 `@RestController`를 사용한다.
* [x] API 응답은 `ResponseEntity`를 사용하도록 구성한다.

### 예약 목록 조회 기능

* [x] `GET /reservations` 요청을 처리한다.
* [x] `@GetMapping("/reservations")`을 이용해 예약 목록 조회 요청을 매핑한다.
* [x] `ReservationService`의 예약 목록 조회 기능을 사용한다.
* [x] 조회된 전체 예약 목록을 `List<Reservation>` 형태로 전달받는다.
* [x] `ResponseEntity<List<Reservation>>`를 이용해 예약 목록을 응답한다.
* [x] 예약 목록 조회 성공 시 `200 OK` 상태 코드를 응답한다.

### 개별 예약 조회 기능

* [x] `GET /reservations/{id}` 요청을 처리한다.
* [x] `@GetMapping("/reservations/{id}")`을 이용해 개별 예약 조회 요청을 매핑한다.
* [x] `@PathVariable`을 이용해 조회할 예약의 식별자를 전달받는다.
* [x] `ReservationService`를 통해 예약을 조회한다.
* [x] 예약이 존재하는 경우 `ResponseEntity<Reservation>`을 이용해 예약 정보를 응답한다.
* [x] 예약 조회 성공 시 `200 OK` 상태 코드를 응답한다.
* [x] 존재하지 않는 예약 조회 시 `GlobalExceptionHandler`를 통해 `404 Not Found`를 응답한다.

### 예약 추가 기능

* [x] `POST /reservations` 요청을 처리한다.
* [x] `@PostMapping("/reservations")`을 이용해 예약 추가 요청을 매핑한다.
* [x] `@RequestBody`를 이용해 요청 본문의 JSON 데이터를 `ReservationRequest`로 전달받는다.
* [x] 예약자 이름, 예약 날짜, 시간 식별자를 `ReservationService`에 전달한다.
* [x] 생성된 `Reservation` 객체를 전달받는다.
* [x] 예약 생성 성공 시 `201 Created` 상태 코드를 응답한다.
* [x] `Location` 헤더에 생성된 예약의 경로(`/reservations/{id}`)를 담아 응답한다.
* [x] 생성된 예약 정보를 응답 본문에 반환한다.

### 예약 삭제 기능

* [x] `DELETE /reservations/{id}` 요청을 처리한다.
* [x] `@DeleteMapping("/reservations/{id}")`을 이용해 예약 삭제 요청을 매핑한다.
* [x] `@PathVariable`을 이용해 삭제할 예약의 식별자를 전달받는다.
* [x] 예약 삭제 성공 시 `204 No Content` 상태 코드를 응답한다.
* [x] `ResponseEntity<Void>`를 반환해 상태 코드만 전달한다.

---

## `TimeController`

### 시간 API 관리 기능

* [x] 시간 관련 HTTP 요청과 응답을 처리한다.
* [x] `TimeService`를 생성자 주입을 통해 전달받는다.
* [x] API 응답을 담당하기 위해 `@RestController`를 사용한다.
* [x] API 응답은 `ResponseEntity`를 사용하도록 구성한다.

### 시간 목록 조회 기능

* [x] `GET /times` 요청을 처리한다.
* [x] `@GetMapping("/times")`을 이용해 시간 목록 조회 요청을 매핑한다.
* [x] `TimeService`의 시간 목록 조회 기능을 사용한다.
* [x] 조회된 전체 시간 목록을 `List<Time>` 형태로 전달받는다.
* [x] `ResponseEntity<List<Time>>`를 이용해 시간 목록을 응답한다.
* [x] 시간 목록 조회 성공 시 `200 OK` 상태 코드를 응답한다.

### 개별 시간 조회 기능

* [x] `GET /times/{id}` 요청을 처리한다.
* [x] `@GetMapping("/times/{id}")`을 이용해 개별 시간 조회 요청을 매핑한다.
* [x] `@PathVariable`을 이용해 조회할 시간의 식별자를 전달받는다.
* [x] `TimeService`를 통해 시간을 조회한다.
* [x] 시간 조회 성공 시 `200 OK` 상태 코드를 응답한다.
* [x] 존재하지 않는 시간 조회 시 `404 Not Found`를 응답한다.

### 시간 추가 기능

* [x] `POST /times` 요청을 처리한다.
* [x] `@PostMapping("/times")`을 이용해 시간 추가 요청을 매핑한다.
* [x] `@RequestBody`를 이용해 요청 본문의 JSON 데이터를 `TimeRequest`로 전달받는다.
* [x] 생성된 `Time` 객체를 전달받는다.
* [x] 시간 생성 성공 시 `201 Created` 상태 코드를 응답한다.
* [x] `Location` 헤더에 생성된 시간의 경로(`/times/{id}`)를 담아 응답한다.
* [x] 생성된 시간 정보를 응답 본문에 반환한다.

### 시간 삭제 기능

* [x] `DELETE /times/{id}` 요청을 처리한다.
* [x] `@DeleteMapping("/times/{id}")`을 이용해 시간 삭제 요청을 매핑한다.
* [x] `@PathVariable`을 이용해 삭제할 시간의 식별자를 전달받는다.
* [x] 시간 삭제 성공 시 `204 No Content` 상태 코드를 응답한다.

---

## `ReservationService`

### 예약 비즈니스 로직 관리 기능

* [x] `@Service`를 이용해 Service 객체를 Spring Bean으로 등록한다.
* [x] `ReservationRepository`를 생성자 주입을 통해 전달받는다.
* [x] `TimeRepository`를 생성자 주입을 통해 전달받는다.
* [x] 예약 생성, 조회, 삭제에 필요한 비즈니스 흐름을 담당한다.

### 예약 목록 조회 기능

* [x] `ReservationRepository.getReservations()`를 호출한다.
* [x] 조회된 예약 목록을 `List<Reservation>` 형태로 반환한다.

### 개별 예약 조회 기능

* [x] `ReservationRepository.getReservation(id)`를 호출한다.
* [x] 예약이 존재하면 `Reservation` 객체를 반환한다.
* [x] 예약이 존재하지 않으면 `NoSuchElementException`을 발생시킨다.

### 예약 추가 기능

* [x] 예약자 이름이 `null`이거나 공백 문자열인지 검증한다.
* [x] 예약 날짜가 `null`인지 검증한다.
* [x] 시간 식별자가 `null`인지 검증한다.
* [x] 잘못된 입력값이 있는 경우 `IllegalArgumentException`을 발생시킨다.
* [x] 시간 식별자를 이용해 `TimeRepository`에서 `Time` 객체를 조회한다.
* [x] 시간이 존재하지 않으면 `NoSuchElementException`을 발생시킨다.
* [x] 조회한 `Time` 객체와 예약 정보를 `ReservationRepository`에 전달해 저장한다.
* [x] 저장된 `Reservation` 객체를 반환한다.

### 예약 삭제 기능

* [x] `ReservationRepository.deleteReservation(id)`를 호출한다.
* [x] 삭제된 행의 개수를 이용해 예약 삭제 성공 여부를 확인한다.
* [x] 삭제된 행이 없는 경우 `NoSuchElementException`을 발생시킨다.

---

## `TimeService`

### 시간 비즈니스 로직 관리 기능

* [x] `@Service`를 이용해 Service 객체를 Spring Bean으로 등록한다.
* [x] `TimeRepository`를 생성자 주입을 통해 전달받는다.
* [x] 시간 생성, 조회, 삭제에 필요한 비즈니스 흐름을 담당한다.

### 시간 목록 조회 기능

* [x] `TimeRepository.getTimes()`를 호출한다.
* [x] 조회된 시간 목록을 `List<Time>` 형태로 반환한다.

### 개별 시간 조회 기능

* [x] `TimeRepository.getTime(id)`를 호출한다.
* [x] 시간이 존재하면 `Time` 객체를 반환한다.
* [x] 시간이 존재하지 않으면 `NoSuchElementException`을 발생시킨다.

### 시간 추가 기능

* [x] 전달받은 시간이 `null`인지 검증한다.
* [x] 시간이 `null`인 경우 `IllegalArgumentException`을 발생시킨다.
* [x] 검증을 통과한 시간을 `TimeRepository`에 전달해 저장한다.
* [x] 저장된 `Time` 객체를 반환한다.

### 시간 삭제 기능

* [x] `TimeRepository.deleteTime(id)`를 호출한다.
* [x] 삭제된 행의 개수를 이용해 시간 삭제 성공 여부를 확인한다.
* [x] 삭제된 행이 없는 경우 `NoSuchElementException`을 발생시킨다.

---

## `ReservationRepository`

### 데이터베이스 접근 기능

* [x] 예약 데이터에 대한 데이터베이스 접근 책임을 담당한다.
* [x] `@Repository`를 이용해 Repository 객체를 Spring Bean으로 등록한다.
* [x] `JdbcTemplate`을 이용해 예약 데이터를 조회하고 삭제한다.
* [x] `SimpleJdbcInsert`를 이용해 예약 데이터를 추가한다.
* [x] 데이터베이스의 자동 생성 키를 이용해 예약 식별자를 생성한다.

### 예약 목록 조회 기능

* [x] `reservation` 테이블과 `time` 테이블을 `INNER JOIN`하여 조회한다.
* [x] 예약 식별자, 예약자 이름, 예약 날짜, 시간 식별자, 실제 시간 값을 함께 조회한다.
* [x] `JdbcTemplate.query()`를 이용해 전체 예약 목록을 조회한다.
* [x] 조회된 각 행을 `Reservation` 객체로 변환한다.
* [x] 조회 결과를 `List<Reservation>` 형태로 반환한다.

```sql
SELECT
    r.id as reservation_id,
    r.name,
    r.date,
    t.id as time_id,
    t.time as time_value
FROM reservation as r
INNER JOIN time as t ON r.time_id = t.id
```

### 개별 예약 조회 기능

* [x] 예약 식별자를 조건으로 `reservation` 테이블과 `time` 테이블을 `INNER JOIN`하여 조회한다.
* [x] `WHERE r.id = ?` 조건을 이용해 특정 예약을 조회한다.
* [x] `JdbcTemplate.query()`를 이용해 조회한다.
* [x] 조회 결과를 `List<Reservation>`으로 전달받는다.
* [x] `stream().findFirst()`를 이용해 조회 결과를 `Optional<Reservation>`으로 변환한다.
* [x] 예약이 존재하면 `Optional`에 예약 객체를 담아 반환한다.
* [x] 예약이 존재하지 않으면 `Optional.empty()`를 반환한다.

### 조회 결과 변환 기능

* [x] 데이터베이스 조회 결과를 `Reservation` 객체로 변환하는 로직을 `mapReservation()` 메서드로 분리한다.
* [x] `ResultSet`에서 `reservation_id`, `name`, `date`, `time_id`, `time_value` 값을 가져온다.
* [x] 문자열 형태의 날짜를 `LocalDate`로 변환한다.
* [x] 문자열 형태의 시간을 `LocalTime`으로 변환한다.
* [x] 조회한 시간 식별자와 시간 값을 이용해 `Time` 객체를 생성한다.
* [x] 생성한 `Time` 객체를 포함해 `Reservation` 객체를 생성한다.
* [x] 전체 조회와 개별 조회에서 발생하는 `ResultSet` 변환 코드의 중복을 제거한다.

### 예약 추가 기능

* [x] `MapSqlParameterSource`를 이용해 저장할 예약 데이터를 구성한다.
* [x] 예약자 이름과 예약 날짜를 데이터베이스에 저장한다.
* [x] `Time` 객체의 식별자를 `time_id`에 저장한다.
* [x] `SimpleJdbcInsert`를 이용해 `reservation` 테이블에 예약 정보를 저장한다.
* [x] `usingGeneratedKeyColumns("id")`를 이용해 데이터베이스에서 생성되는 식별자를 지정한다.
* [x] `executeAndReturnKey()`를 이용해 새롭게 생성된 예약의 `id`를 반환받는다.
* [x] 생성된 `id`와 저장한 데이터를 이용해 `Reservation` 객체를 생성한다.
* [x] 생성된 `Reservation` 객체를 반환한다.

### 예약 삭제 기능

* [x] `DELETE FROM reservation WHERE id = ?` 쿼리를 사용한다.
* [x] `JdbcTemplate.update()`를 이용해 데이터베이스에서 예약 정보를 삭제한다.
* [x] `JdbcTemplate.update()`의 반환값을 이용해 실제 삭제된 행의 개수를 확인한다.
* [x] 삭제된 행의 개수를 반환한다.

---

## `TimeRepository`

### 데이터베이스 접근 기능

* [x] 시간 데이터에 대한 데이터베이스 접근 책임을 담당한다.
* [x] `@Repository`를 이용해 Repository 객체를 Spring Bean으로 등록한다.
* [x] `JdbcTemplate`을 이용해 시간 데이터를 조회하고 삭제한다.
* [x] `SimpleJdbcInsert`를 이용해 시간 데이터를 추가한다.
* [x] 데이터베이스의 자동 생성 키를 이용해 시간 식별자를 생성한다.

### 시간 목록 조회 기능

* [x] `SELECT id, time FROM TIME` 쿼리를 사용한다.
* [x] `JdbcTemplate.query()`를 이용해 전체 시간 목록을 조회한다.
* [x] 조회된 각 행을 `Time` 객체로 변환한다.
* [x] 조회 결과를 `List<Time>` 형태로 반환한다.

### 개별 시간 조회 기능

* [x] `SELECT id, time FROM TIME WHERE id = ?` 쿼리를 사용한다.
* [x] `JdbcTemplate.query()`를 이용해 특정 시간을 조회한다.
* [x] 조회 결과를 `List<Time>` 형태로 전달받는다.
* [x] `stream().findFirst()`를 이용해 조회 결과를 `Optional<Time>`으로 변환한다.
* [x] 시간이 존재하면 `Optional`에 시간 객체를 담아 반환한다.
* [x] 시간이 존재하지 않으면 `Optional.empty()`를 반환한다.

### 시간 추가 기능

* [x] `MapSqlParameterSource`를 이용해 저장할 시간 데이터를 구성한다.
* [x] `LocalTime` 값을 문자열로 변환해 데이터베이스에 저장한다.
* [x] `SimpleJdbcInsert`를 이용해 `TIME` 테이블에 시간 정보를 저장한다.
* [x] `usingGeneratedKeyColumns("id")`를 이용해 데이터베이스에서 생성되는 식별자를 지정한다.
* [x] `executeAndReturnKey()`를 이용해 새롭게 생성된 시간의 `id`를 반환받는다.
* [x] 생성된 `id`와 저장한 시간을 이용해 `Time` 객체를 생성한다.
* [x] 생성된 `Time` 객체를 반환한다.

### 시간 삭제 기능

* [x] `DELETE FROM TIME WHERE id = ?` 쿼리를 사용한다.
* [x] `JdbcTemplate.update()`를 이용해 데이터베이스에서 시간 정보를 삭제한다.
* [x] `JdbcTemplate.update()`의 반환값을 이용해 실제 삭제된 행의 개수를 확인한다.
* [x] 삭제된 행의 개수를 반환한다.

### 조회 결과 변환 기능

* [x] 데이터베이스 조회 결과를 `Time` 객체로 변환하는 로직을 `mapTime()` 메서드로 분리한다.
* [x] `ResultSet`에서 `id`, `time` 값을 가져온다.
* [x] 문자열 형태의 시간을 `LocalTime`으로 변환한다.
* [x] 조회한 식별자와 시간을 이용해 `Time` 객체를 생성한다.

---

## `GlobalExceptionHandler`

### 전역 예외 처리 기능

* [x] `@ControllerAdvice`를 이용해 컨트롤러에서 발생하는 예외를 공통으로 처리한다.
* [x] `@ExceptionHandler`를 이용해 예외 종류에 따라 다른 응답을 반환한다.

### 잘못된 요청 처리

* [x] `IllegalArgumentException`이 발생하는 경우를 처리한다.
* [x] 필요한 예약 또는 시간 정보가 비어 있는 요청에 대해 `400 Bad Request`를 응답한다.

### 존재하지 않는 데이터 처리

* [x] `NoSuchElementException`이 발생하는 경우를 처리한다.
* [x] 조회하거나 삭제하려는 예약 또는 시간을 찾을 수 없는 경우 `404 Not Found`를 응답한다.

---

## `Reservation`

### 예약 정보 관리

* [x] 예약 식별자를 `long` 타입의 `id`로 저장한다.
* [x] 예약자 이름을 `String` 타입의 `name`으로 저장한다.
* [x] 예약 날짜를 `LocalDate` 타입의 `date`로 저장한다.
* [x] 예약 시간을 `Time` 타입의 `time`으로 저장한다.
* [x] 모든 필드에 `final`을 적용해 객체 생성 이후 다른 값으로 변경되지 않도록 한다.
* [x] 생성자를 통해 예약 식별자, 예약자 이름, 예약 날짜, 예약 시간 객체를 전달받는다.
* [x] getter 메서드를 통해 private 필드의 값을 확인할 수 있도록 한다.

---

## `Time`

### 시간 정보 관리

* [x] 시간 식별자를 `long` 타입의 `id`로 저장한다.
* [x] 실제 시간을 `LocalTime` 타입의 `time`으로 저장한다.
* [x] 모든 필드에 `final`을 적용해 객체 생성 이후 다른 값으로 변경되지 않도록 한다.
* [x] 생성자를 통해 시간 식별자와 실제 시간을 전달받는다.
* [x] getter 메서드를 통해 private 필드의 값을 확인할 수 있도록 한다.

---

## `ReservationRequest`

### 예약 생성 요청 데이터 관리

* [x] 예약자 이름을 `String` 타입의 `name`으로 전달받는다.
* [x] 예약 날짜를 `LocalDate` 타입의 `date`로 전달받는다.
* [x] 예약 시간의 식별자를 `Long` 타입의 `time`으로 전달받는다.
* [x] 모든 필드에 `final`을 적용한다.
* [x] 생성자를 통해 예약 생성에 필요한 값을 전달받는다.
* [x] getter 메서드를 통해 요청 데이터를 확인할 수 있도록 한다.
* [x] 예약 시간 문자열 대신 `Time` 테이블의 식별자를 요청값으로 사용한다.

### 예약 생성 요청 형식

```json
{
    "name": "브라운",
    "date": "2023-08-05",
    "time": 1
}
```

---

## `TimeRequest`

### 시간 생성 요청 데이터 관리

* [x] 시간을 `LocalTime` 타입의 `time`으로 전달받는다.
* [x] `final` 필드를 사용한다.
* [x] `@JsonCreator`를 이용해 JSON 요청을 객체로 생성한다.
* [x] `@JsonProperty("time")`을 이용해 JSON의 `time` 값을 생성자 인자와 연결한다.
* [x] getter 메서드를 통해 요청 데이터를 확인할 수 있도록 한다.

### 시간 생성 요청 형식

```json
{
    "time": "10:00"
}
```

---

## 레이어드 아키텍처

### Presentation Layer

* [x] `PageController`가 페이지 요청과 View 응답을 처리한다.
* [x] `ReservationController`가 예약 관련 HTTP 요청과 응답을 처리한다.
* [x] `TimeController`가 시간 관련 HTTP 요청과 응답을 처리한다.
* [x] Controller는 데이터베이스에 직접 접근하지 않는다.
* [x] Controller는 Service 계층에 비즈니스 처리를 위임한다.

### Service Layer

* [x] `ReservationService`가 예약 관련 비즈니스 흐름을 처리한다.
* [x] `TimeService`가 시간 관련 비즈니스 흐름을 처리한다.
* [x] 입력값 검증을 Service 계층에서 처리한다.
* [x] 예약 생성 시 시간 식별자를 이용해 실제 `Time` 객체를 조회한다.
* [x] Service 계층에서 Repository 계층을 호출한다.

### Data Access Layer

* [x] `ReservationRepository`가 예약 데이터베이스 접근을 담당한다.
* [x] `TimeRepository`가 시간 데이터베이스 접근을 담당한다.
* [x] `JdbcTemplate`과 `SimpleJdbcInsert`를 Repository 계층에서 사용한다.
* [x] SQL 실행과 조회 결과 변환을 Repository 계층에서 처리한다.

### Domain

* [x] `Reservation`이 예약 정보를 관리한다.
* [x] `Time`이 시간 정보를 관리한다.
* [x] `Reservation`이 `Time` 객체를 필드로 가지도록 구성한다.

---

## Spring Bean

### Bean 등록 및 의존성 주입

* [x] `@Controller`, `@RestController`를 이용해 Controller를 Spring Bean으로 등록한다.
* [x] `@Service`를 이용해 Service를 Spring Bean으로 등록한다.
* [x] `@Repository`를 이용해 Repository를 Spring Bean으로 등록한다.
* [x] 생성자 주입을 이용해 계층 간 의존성을 전달한다.
* [x] 객체를 직접 생성하지 않고 Spring Container가 Bean의 생성과 관리를 담당하도록 구성한다.
* [x] `ReservationController`에서 `JdbcTemplate` 필드를 제거한다.
* [x] Controller가 Repository에 직접 접근하지 않고 Service를 통해 기능을 수행하도록 구성한다.
