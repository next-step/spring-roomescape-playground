# Roomescape (Reservation & Time)
___

## Request & Response 명세

### 1. 예약(Reservation) API
* **예약 목록 조회**
    * `GET /reservations HTTP/1.1`
    * 응답: `200 OK` (JSON 배열)
* **예약 추가**
    * `POST /reservations HTTP/1.1` (body: name, date, time)
    * 응답: `201 Created` (Location: `/reservations/{id}`)
* **예약 취소**
    * `DELETE /reservations/{id} HTTP/1.1`
    * 응답: `204 No Content`

### 2. 시간(Time) API
* **시간 목록 조회**
    * `GET /times HTTP/1.1`
    * 응답: `200 OK`
      ```json
      [
        { "id": 1, "time": "10:00" },
        { "id": 2, "time": "11:00" }
      ]
      ```
* **시간 추가**
    * `POST /times HTTP/1.1`
      ```json
      { "time": "15:40" }
      ```
    * 응답: `201 Created` (Location: `/times/{id}`)
* **시간 삭제**
    * `DELETE /times/{id} HTTP/1.1`
    * 응답: `204 No Content`

___

## 프로그래밍 요구사항

- [x] `localhost:8080` 요청 시 어드민 메인 페이지(`templates/home.html`)가 응답하도록 구현하세요.
- [x] `/reservation` 요청 시 예약 관리 페이지(`templates/reservation.html`)가 응답하도록 구현하세요.
- [x] `/time` 요청 시 시간 관리 페이지(`templates/time.html`)가 응답하도록 구현하세요.
- [x] h2 데이터베이스를 활용하여 데이터를 저장하고 관리하도록 수정하세요.
- [x] 예약 및 시간 조회 API 처리 로직에서 데이터베이스를 활용하도록 구현하세요.
- [x] 예약 및 시간 추가/삭제 API 처리 로직에서 데이터베이스를 활용하도록 구현하세요.
- [x] API 호출 시 에러가 발생하는 경우 중 클라이언트 요청의 문제인 경우 적절한 HTTP Status Code(400, 404, 409)로 응답하세요.

___

## 클래스 정리

### Time
시간 정보를 담당하는 도메인 모델로, 데이터 저장 및 식별자 기반의 식별성을 보장한다.

### TimeRequest
클라이언트의 시간 등록 요청 데이터를 담는 DTO이다. Jackson의 데이터 바인딩을 위해 **기본 생성자**를 포함하며, `@JsonFormat(pattern = "HH:mm")`을 통해 문자열 시간을 `LocalTime`으로 변환한다.

### TimeResponse
클라이언트에게 전달할 시간 응답 데이터를 담는 DTO이다.

---

### Reservation
예약 정보를 담당하는 도메인 모델로, 데이터 저장뿐만 아니라 데이터의 유효성 검증을 스스로 수행한다. id 기반의 `equals/hashCode`를 통해 객체의 식별성을 보장한다.

#### validate
생성 시점에 이름, 날짜, 시간의 누락 여부를 확인하고, 비즈니스 규칙에 어긋나는 예약을 방지한다.

#### toEntity
`ReservationRequest` 정보와 새로운 id를 받았을 때, 이를 조합하여 유효성이 검증된 완전한 `Reservation` 엔티티 객체를 생성하고 반환한다.

### ReservationRequest
클라이언트의 예약 요청 데이터를 담는 DTO로, 엔티티와 정체성을 분리하여 id가 없는 상태의 데이터를 관리한다.

### ReservationResponse
클라이언트에게 전달할 예약 응답 데이터를 담는 DTO로, `@JsonFormat` 등을 통해 노출할 데이터의 형식을 제어한다.

---

### RoomescapeController
`JdbcTemplate`과 `SimpleJdbcInsert`를 통해 데이터베이스와 직접 통신하며 예약 및 시간 데이터를 관리하는 컨트롤러이다.

#### showHomePage / showReservationPage / showTimePage
각각 `home.html`, `reservation.html`, `time.html` 화면을 보여준다.

#### getTimes / addTime / deleteTime
* DB에서 전체 시간 목록을 조회하여 반환한다.
* 새로운 시간을 추가하고, H2 예약어 충돌을 방지하기 위해 백틱(`` `time` ``)이 적용된 테이블에 데이터를 삽입한 후 201 코드를 반환한다.
* id에 해당하는 시간을 DB에서 삭제하며, 영향받은 행이 없으면 `NotFoundTimeException`을 던진다.

#### getReservations / addReservation / deleteReservation
* DB에서 전체 예약 목록을 조회하여 반환한다.
* `reservationValidator.validateDuplicate`를 통해 중복 예약을 확인한 후 예약을 저장한다. 성공 시 201 코드를 반환한다.
* id에 해당하는 예약을 삭제하며, 삭제할 데이터가 없으면 `NotFoundReservationException`을 던진다.

#### handleException (예외 핸들러)
발생한 커스텀 예외들을 잡아 적절한 HTTP 상태 코드와 함께 에러 메시지를 바디(Body)에 담아 응답한다.

---

### 커스텀 예외(Exception) 클래스

* **NotFoundReservationException**: 삭제할 예약 리소스가 존재하지 않을 때 사용하며, `404 Not Found`로 응답한다.
* **NotFoundTimeException**: 삭제할 시간 리소스가 존재하지 않을 때 사용하며, `404 Not Found`로 응답한다.
* **InvalidReservationException**: 입력 데이터 규칙이 어긋나거나 필수 값이 누락되었을 때 사용하며, `400 Bad Request`로 응답한다.
* **ReservationConflictException**: 이미 동일한 시간에 예약이 존재할 때 사용하며, `409 Conflict`로 응답한다.

___

## 예외 처리 규칙
* 삭제하려는 해당 id의 예약 또는 시간이 존재하지 않을 때 (`404 Not Found`)
* 예약 및 시간 정보 중 필수 값이 누락되었거나 형식 오류일 때 (`400 Bad Request`)
* 과거 날짜나 이미 지난 시간으로 예약을 시도할 때 (`400 Bad Request`)
* 이미 예약된 시간이나 존재하는 시간에 중복 요청을 시도할 때 (`409 Conflict`)
