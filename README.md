# Roomescape (Reservation & Time)
___

## Request & Response 명세

### 1. 예약(Reservation) API
* **예약 목록 조회**
    * `GET /reservations HTTP/1.1`
    * 응답: `200 OK` (JSON 배열)
* **예약 추가**
    * `POST /reservations HTTP/1.1` (body: name, date, timeId)
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
- [x] `/reservation` 요청 시 예약 관리 페이지(`templates/new-reservation.html`)가 응답하도록 구현하세요.
- [x] `/time` 요청 시 시간 관리 페이지(`templates/time.html`)가 응답하도록 구현하세요.
- [x] h2 데이터베이스를 활용하여 데이터를 저장하고 관리하도록 수정하세요.
- [x] 예약 및 시간 조회 API 처리 로직에서 데이터베이스를 활용하도록 구현하세요.
- [x] 예약 및 시간 추가/삭제 API 처리 로직에서 데이터베이스를 활용하도록 구현하세요.
- [x] API 호출 시 에러가 발생하는 경우 중 클라이언트 요청의 문제인 경우 적절한 HTTP Status Code(400, 404, 409)로 응답하세요.
- [x] 레이어드 아키텍처(Layered Architecture)를 적용하여 각 계층의 객체들을 스프링 빈(Bean)으로 등록하고 제어의 역전(IoC) 및 의존성 주입(DI)을 통해 관리하세요.

___

## 클래스 정리

### 도메인 (Domain)

#### Time
시간 정보를 담당하는 도메인 모델로, 데이터 저장 및 식별자 기반의 식별성을 보장한다.

#### Times
등록된 시간 목록을 포장하는 **일급 컬렉션**이다. 내부 시간 목록을 불변 리스트로 안전하게 관리한다.

#### Reservation
예약 정보를 담당하는 도메인 모델로, 데이터 저장뿐만 아니라 데이터의 유효성 검증을 스스로 수행한다. id 기반의 `equals/hashCode`를 통해 객체의 식별성을 보장한다.

#### Reservations
등록된 예약 목록을 포장하는 **일급 컬렉션**이다. 특정 조건 하에 중복된 예약이 존재하는지 확인하는 비즈니스 규칙 검증을 내포한다.

---

### 데이터 전송 객체 (DTO)

#### TimeRequest
클라이언트의 시간 등록 요청 데이터를 담는 DTO이다. Jackson의 데이터 바인딩을 위해 **기본 생성자**를 포함하며, `@JsonFormat(pattern = "HH:mm")`을 통해 문자열 시간을 `LocalTime`으로 변환한다.

#### TimeResponse
클라이언트에게 전달할 시간 응답 데이터를 담는 DTO이다.

#### ReservationRequest
클라이언트의 예약 요청 데이터를 담는 DTO로, 엔티티와 정체성을 분리하여 id가 없는 상태의 데이터를 관리한다. 연관 관계 생성을 위한 `timeId`를 포함한다.

#### ReservationResponse
클라이언트에게 전달할 예약 응답 데이터를 담는 DTO로, `@JsonFormat` 등을 통해 노출할 데이터의 형식을 제어한다.

---

### 컨트롤러 (Controller) 계층
스프링 컨테이너의 빈으로 등록되어 클라이언트의 HTTP 요청을 받고 응답하는 웹 계층의 역할을 수행한다.

#### RoomescapeController
기존 API 처리를 분리하고, 순수하게 메인 홈 화면(`home.html`), 예약 관리 화면(`new-reservation.html`), 시간 관리 화면(`time.html`) 뷰를 보여주는 역할만 전담한다.

#### TimeController
시간 관련 API 요청을 처리하는 컨트롤러이다. `@RestController`와 `@RequestMapping("/times")`을 적용하여 공통 경로와 JSON 응답을 효율적으로 관리하며, 비즈니스 로직 처리를 `RoomescapeService`에 위임한다.

#### ReservationController
예약 관련 API 요청을 처리하는 컨트롤러이다. `@RestController`와 `@RequestMapping("/reservations")`을 적용하여 예약의 조회, 추가, 삭제 요청을 받고 결과를 반환한다.

---

### 서비스 (Service) 계층

#### RoomescapeService
애플리케이션의 핵심 비즈니스 로직 흐름을 제어하는 계층이다. 리포지토리로부터 데이터를 조회한 뒤 일급 컬렉션(`Times`, `Reservations`) 객체로 포장하여 비즈니스 규칙을 수행하며, 검증 컴포넌트(Validator)들을 조합하여 데이터의 정당성을 확인한다.

---

### 리포지토리 (Repository) 계층
데이터베이스 접근 로직을 전담하는 계층으로, 스프링 빈으로 등록되어 싱글톤으로 관리된다.

#### TimeRepository
`JdbcTemplate`과 `SimpleJdbcInsert`를 사용해 `time` 테이블에 접근하며 시간 데이터를 조회, 저장, 삭제하는 역할을 전담한다.

#### ReservationRepository
`JdbcTemplate`과 `SimpleJdbcInsert`를 사용해 `reservation` 테이블에 접근하며, `time` 테이블과의 `INNER JOIN`을 통해 예약 목록을 완전한 객체 그래프 형태로 복원해온다.

---

### 검증 (Validator) 컴포넌트

#### TimeValidator
시간 데이터를 추가할 때 리포지토리를 통해 동일한 시간이 이미 데이터베이스에 존재하는지 중복 여부를 검증한다.

#### ReservationValidator
예약을 생성할 때 리포지토리로부터 해당 날짜의 예약 데이터를 조회한 뒤, `Reservations` 일급 컬렉션을 활용해 중복 예약 여부를 확인하고 비즈니스 규칙을 강제한다.

---

### 커스텀 예외(Exception) 클래스

* **NotFoundReservationException**: 삭제할 예약 리소스가 존재하지 않을 때 사용하며, `404 Not Found`로 응답한다.
* **NotFoundTimeException**: 삭제할 시간 리소스가 존재하지 않을 때 사용하며, `404 Not Found`로 응답한다.
* **InvalidReservationException**: 입력 데이터 규칙이 어긋나거나 필수 값이 누락되었을 때, 혹은 존재하지 않는 시간 ID로 예약을 시도할 때 사용하며 `400 Bad Request`로 응답한다.
* **ReservationConflictException**: 이미 동일한 시간에 예약이 존재할 때 사용하며, `409 Conflict`로 응답한다.

___

## 예외 처리 규칙
* 삭제하려는 해당 id의 예약 또는 시간이 존재하지 않을 때 (`404 Not Found`)
* 예약 및 시간 정보 중 필수 값이 누락되었거나 형식 오류일 때 (`400 Bad Request`)
* 과거 날짜나 이미 지난 시간으로 예약을 시도할 때 (`400 Bad Request`)
* 이미 예약된 시간이나 존재하는 시간에 중복 요청을 시도할 때 (`409 Conflict`)

```
