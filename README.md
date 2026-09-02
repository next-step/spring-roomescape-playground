# spring-roomescape-playground

## 클래스별 구현 기능 목록

---

## `ReservationController`

### 예약 데이터 관리 기능

* [x] 예약 정보를 H2 데이터베이스에 저장한다.
* [x] `JdbcTemplate`을 이용해 예약 데이터를 조회하고 삭제한다.
* [x] `SimpleJdbcInsert`를 이용해 예약 데이터를 추가한다.
* [x] 데이터베이스의 자동 생성 키를 이용해 예약 식별자를 생성한다.

### 예약 페이지 응답 기능

* [x] `GET /reservation` 요청을 처리한다.
* [x] `@GetMapping("/reservation")`을 이용해 예약 페이지 요청을 매핑한다.
* [x] `"reservation"`을 반환한다.
* [x] Thymeleaf를 통해 `templates/reservation.html`을 응답한다.

### 예약 목록 조회 기능

* [x] `GET /reservations` 요청을 처리한다.
* [x] `@GetMapping("/reservations")`을 이용해 예약 목록 조회 요청을 매핑한다.
* [x] `JdbcTemplate.query()`를 이용해 데이터베이스에서 예약 목록을 조회한다.
* [x] `SELECT id, name, date, time FROM reservation` 쿼리를 사용한다.
* [x] 조회한 각 행을 `Reservation` 객체로 변환한다.
* [x] `ResultSet`을 이용해 `id`, `name`, `date`, `time` 값을 가져온다.
* [x] 문자열 형태의 날짜와 시간을 `LocalDate`, `LocalTime`으로 변환한다.
* [x] 조회된 예약 목록을 `List<Reservation>` 형태로 반환한다.
* [x] `ResponseEntity<List<Reservation>>`를 이용해 `200 OK`와 예약 목록을 응답한다.

### 개별 예약 조회 기능

* [x] `GET /reservations/{id}` 요청을 처리한다.
* [x] `@GetMapping("/reservations/{id}")`을 이용해 개별 예약 조회 요청을 매핑한다.
* [x] `@PathVariable`을 이용해 조회할 예약의 식별자를 전달받는다.
* [x] `JdbcTemplate.queryForObject()`를 이용해 특정 예약을 조회한다.
* [x] `SELECT id, name, date, time FROM reservation WHERE id = ?` 쿼리를 사용한다.
* [x] 조회된 데이터를 `Reservation` 객체로 변환한다.
* [x] `@ResponseBody`를 이용해 조회된 예약 정보를 JSON 형식으로 응답한다.

### 예약 추가 기능

* [x] `POST /reservations` 요청을 처리한다.
* [x] `@PostMapping("/reservations")`을 이용해 예약 추가 요청을 매핑한다.
* [x] `@RequestBody`를 이용해 요청 본문의 JSON 데이터를 `ReservationRequest`로 전달받는다.
* [x] 예약자 이름이 `null`이거나 공백 문자열인지 검증한다.
* [x] 예약 날짜와 시간이 `null`인지 검증한다.
* [x] `MapSqlParameterSource`를 이용해 저장할 예약 데이터를 구성한다.
* [x] `SimpleJdbcInsert`를 이용해 `reservation` 테이블에 예약 정보를 저장한다.
* [x] `usingGeneratedKeyColumns("id")`를 이용해 데이터베이스에서 생성된 예약 식별자를 사용한다.
* [x] `executeAndReturnKey()`를 이용해 새롭게 생성된 예약의 `id`를 반환받는다.
* [x] 생성된 `id`와 요청 데이터를 이용해 `Reservation` 객체를 생성한다.
* [x] 예약 생성 성공 시 `201 Created` 상태 코드를 응답한다.
* [x] `Location` 헤더에 생성된 예약의 경로(`/reservations/{id}`)를 담아 응답한다.
* [x] 생성된 예약 정보를 응답 본문에 반환한다.

### 예약 추가 예외 처리

* [x] 예약자 이름, 날짜, 시간 중 필요한 값이 비어 있는지 확인한다.
* [x] 예약자 이름이 `null` 또는 공백 문자열인 경우를 검증한다.
* [x] 날짜 또는 시간이 `null`인 경우를 검증한다.
* [x] 잘못된 입력값이 있는 경우 `IllegalArgumentException`을 발생시킨다.
* [x] 발생한 예외는 `GlobalExceptionHandler`에서 처리한다.
* [x] 잘못된 예약 추가 요청에 대해 `400 Bad Request`를 응답한다.

### 예약 삭제 기능

* [x] `DELETE /reservations/{id}` 요청을 처리한다.
* [x] `@DeleteMapping("/reservations/{id}")`을 이용해 예약 삭제 요청을 매핑한다.
* [x] `@PathVariable`을 이용해 삭제할 예약의 식별자를 전달받는다.
* [x] `DELETE FROM reservation WHERE id = ?` 쿼리를 사용한다.
* [x] `JdbcTemplate.update()`를 이용해 데이터베이스에서 예약 정보를 삭제한다.
* [x] `JdbcTemplate.update()`의 반환값으로 실제 삭제된 행의 개수를 확인한다.
* [x] 삭제된 행이 없는 경우 `NoSuchElementException`을 발생시킨다.
* [x] 예약 삭제 성공 시 `204 No Content` 상태 코드를 응답한다.
* [x] `ResponseEntity<Void>`를 반환해 상태 코드만 전달한다.

### 예약 삭제 예외 처리

* [x] 삭제할 예약이 존재하지 않는 경우 `NoSuchElementException`을 발생시킨다.
* [x] 발생한 예외는 `GlobalExceptionHandler`에서 처리한다.
* [x] 삭제할 예약을 찾을 수 없는 경우 `404 Not Found`를 응답한다.
* [x] 동일한 예약을 다시 삭제하는 경우 이미 예약이 존재하지 않으므로 `404 Not Found`를 응답한다.

---

## `GlobalExceptionHandler`

### 전역 예외 처리 기능

* [x] `@ControllerAdvice`를 이용해 컨트롤러에서 발생하는 예외를 공통으로 처리한다.
* [x] `@ExceptionHandler`를 이용해 예외 종류에 따라 다른 응답을 반환한다.

### 잘못된 요청 처리

* [x] `IllegalArgumentException`이 발생하는 경우를 처리한다.
* [x] 필요한 예약 정보가 비어 있는 요청에 대해 `400 Bad Request`를 응답한다.

### 존재하지 않는 예약 처리

* [x] `NoSuchElementException`이 발생하는 경우를 처리한다.
* [x] 삭제하려는 예약을 찾을 수 없는 경우 `404 Not Found`를 응답한다.

---

## `Reservation`

### 예약 정보 관리

* [x] 예약 식별자를 `long` 타입의 `id`로 저장한다.
* [x] 예약자 이름을 `String` 타입의 `name`으로 저장한다.
* [x] 예약 날짜를 `LocalDate` 타입의 `date`로 저장한다.
* [x] 예약 시간을 `LocalTime` 타입의 `time`으로 저장한다.
* [x] 모든 필드에 `final`을 적용해 객체 생성 이후 다른 값으로 변경되지 않도록 한다.
* [x] 생성자를 통해 예약 식별자, 예약자 이름, 예약 날짜, 예약 시간을 전달받는다.
* [x] getter 메서드를 통해 private 필드의 값을 확인할 수 있도록 한다.

---

## `ReservationRequest`

### 예약 생성 요청 데이터 관리

* [x] 예약자 이름을 `String` 타입의 `name`으로 전달받는다.
* [x] 예약 날짜를 `LocalDate` 타입의 `date`로 전달받는다.
* [x] 예약 시간을 `LocalTime` 타입의 `time`으로 전달받는다.
* [x] 모든 필드에 `final`을 적용한다.
* [x] 생성자를 통해 예약 생성에 필요한 값을 전달받는다.
* [x] getter 메서드를 통해 요청 데이터를 확인할 수 있도록 한다.
