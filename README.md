# spring-roomescape-playground

---

## User Requirement

- 사용자는 어드민 메인 페이지에 접속할 수 있다.
- 사용자는 예약 관리 페이지에서 전체 예약 목록을 조회할 수 있다.
- 사용자는 예약 관리 페이지에서 예약을 추가할 수 있다.
- 사용자는 예약 관리 페이지에서 예약을 취소할 수 있다.

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
