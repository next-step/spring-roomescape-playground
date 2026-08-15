# spring-roomescape-playground

## 클래스별 구현 기능 목록

---

## `ReservationController`

### 예약 데이터 관리 기능

* [x] 예약 목록을 `List<Reservation>`으로 관리한다. (ArrayList)
* [x] 예약 목록 필드에 `final`을 적용해 다른 리스트 객체로 바뀌지 않도록 한다.
* [x] 컨트롤러 생성 시 정상 동작 확인을 위한 임의의 예약 데이터를 추가한다.
* [x] 예약 목록에 2개의 임의 예약 데이터를 저장한다.

### 예약 페이지 응답 기능

* [x] `GET /reservation` 요청을 처리한다.
* [x] `@GetMapping("/reservation")`을 이용해 예약 페이지 요청을 매핑한다.
* [x] `"reservation"`을 반환한다.
* [x] Thymeleaf를 통해 `templates/reservation.html`을 응답한다.

### 예약 목록 조회 기능

* [x] `GET /reservations` 요청을 처리한다.
* [x] `@GetMapping("/reservations")`을 이용해 예약 목록 조회 요청을 매핑한다.
* [x] `@ResponseBody`를 이용해 반환값을 HTTP 응답 본문에 전달한다.
* [x] 저장된 전체 예약 목록을 `List<Reservation>` 형태로 반환한다.
* [x] 예약 목록을 JSON 형식의 응답으로 반환할 수 있도록 한다.

---

## `Reservation`

* [x] 예약 식별자를 `long` 타입의 `id`로 저장한다.
* [x] 예약자 이름을 `String` 타입의 `name`으로 저장한다.
* [x] 예약 날짜를 `String` 타입의 `date`로 저장한다.
* [x] 예약 시간을 `String` 타입의 `time`으로 저장한다.
* [x] 모든 필드에 `final`을 적용해 객체 생성 이후 다른 값으로 바뀌지 않도록 한다.
* [x] 생성자를 통해 예약 식별자, 예약자 이름, 예약 날짜, 예약 시간을 전달받는다.
* [x] getter 메서드를 통해 private 필드의 값을 확인할 수 있도록 한다.
