# spring-roomescape-playground

---

## User Requirement

- 사용자는 어드민 메인 페이지에 접속할 수 있다.
- 사용자는 예약 관리 페이지에서 전체 예약 목록을 조회할 수 있다.

## System Requirement

- [x] `GET /` 요청 시 어드민 메인 페이지(`home.html`)를 응답한다.
- [x] `GET /reservation` 요청 시 예약 관리 페이지(`reservation.html`)를 응답한다.
- [x] `GET /reservations` 요청 시 전체 예약 목록을 JSON으로 응답한다.
  - [x] 각 예약은 `id`, `name`, `date`, `time` 필드를 가진다.
