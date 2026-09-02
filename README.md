# spring-roomescape-playground

---

## User Requirement

- 사용자는 어드민 메인 페이지에 접속할 수 있다.
- 사용자는 예약 관리 페이지에서 전체 예약 목록을 조회할 수 있다.
- 사용자는 예약 관리 페이지에서 예약을 추가할 수 있다.
- 사용자는 예약 관리 페이지에서 예약을 취소할 수 있다.
- 사용자는 시간 관리 페이지에서 전체 시간 목록을 조회할 수 있다.
- 사용자는 시간 관리 페이지에서 시간을 추가할 수 있다.
- 사용자는 시간 관리 페이지에서 시간을 삭제할 수 있다.

## System Requirement

- [x] `GET /` 요청 시 어드민 메인 페이지(`home.html`)를 응답한다.
- [x] `GET /reservation` 요청 시 예약 관리 페이지(`reservation.html`)를 응답한다.
- [x] `GET /reservations` 요청 시 전체 예약 목록을 JSON으로 응답한다.
  - [x] 각 예약은 `id`, `name`, `date`, `time` 필드를 가진다.
- [x] `POST /reservations` 요청 시 예약을 추가한다.
  - [x] 요청의 `name`, `date`, `time`으로 예약을 생성하고, 식별자(`id`)는 서버에서 발급한다.
  - [x] 응답은 `201 Created`이며, `Location: /reservations/{id}` 헤더와 생성된 예약을 바디에 포함한다.
- [x] `DELETE /reservations/{id}` 요청 시 해당 예약을 취소한다.
  - [x] 응답은 `204 No Content`이며 본문이 없다.
- [x] 잘못된 요청은 적절한 Status Code로 응답한다.
  - [x] 예약 추가 시 `name`, `date`, `time` 중 비어 있는 값이 있으면 `400 Bad Request`로 응답한다.
  - [x] 삭제할 예약을 식별자로 찾을 수 없으면 `404 Not Found`로 응답한다.
- [x] `GET /times` 요청 시 전체 시간 목록(`id`, `time`)을 JSON으로 응답한다.
- [x] `POST /times` 요청 시 시간을 추가하고, `201 Created`와 `Location: /times/{id}` 헤더, 생성된 시간을 응답한다.
  - [x] 시간 값은 `LocalTime`으로 다룬다.
  - [x] `time`이 비어 있거나 시간 형식이 아니면 `400`, 이미 등록된 시간이면 `409`로 응답한다.
- [x] `DELETE /times/{id}` 요청 시 해당 시간을 삭제하고 `204 No Content`로 응답한다.
  - [x] 삭제할 시간을 찾을 수 없으면 `404`로 응답한다.

## Database Requirement

- [x] `spring-boot-starter-jdbc`, `h2` 의존성을 추가한다.
- [x] `schema.sql`에 예약(`reservation`) 테이블 생성 쿼리를 작성한다.
  - [x] `id`(PK), `name`, `date`, `time` 컬럼을 가진다.
  - [x] 같은 `date`, `time`에 예약이 중복될 수 없도록 유니크 제약을 건다.
- [x] h2 데이터베이스를 설정한다.
  - [x] h2 console 기능을 활성화한다.
  - [x] datasource url을 `jdbc:h2:mem:database`로 지정한다.
- [x] `JdbcTemplate`으로 `DataSource`에 접근해 데이터베이스 연결을 검증한다.
  - [x] `Connection`으로 데이터베이스 이름을 검증한다.
  - [x] `Connection`으로 테이블 이름을 검증한다.
- [x] `GET /reservations`는 `JdbcTemplate`으로 데이터베이스에서 예약을 조회한다.
- [x] `POST /reservations`는 데이터베이스에 예약을 추가하고, 발급된 식별자를 응답한다.
- [x] `DELETE /reservations/{id}`는 데이터베이스에서 예약을 삭제한다.
- [x] `schema.sql`에 시간(`time`) 테이블 생성 쿼리를 작성한다.
  - [x] `id`(PK), `time` 컬럼을 가진다.
  - [x] 같은 `time`에 시간이 중복될 수 없도록 유니크 제약을 건다.
