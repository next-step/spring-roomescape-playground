# 방 탈출 예약 관리 - Spring MVC (1~2단계)

## 미션 소개

Spring MVC를 활용하여 방탈출 어드민 페이지를 구성하고,
예약 관리 페이지와 예약 목록 조회 API를 구현하는 프로젝트

---

## 요구사항

* Spring MVC를 활용하여 웹 요청을 처리한다.
* Thymeleaf 템플릿 엔진을 활용하여 화면을 응답한다.
* `/` 요청 시 어드민 메인 페이지를 응답한다.
* `/reservation` 요청 시 예약 관리 페이지를 응답한다.
* `/reservations` 요청 시 예약 목록을 JSON으로 응답한다.
* 별도의 데이터베이스 없이 예약 목록을 관리한다.
* 예약 조회 응답에는 `id`, `name`, `date`, `time` 정보를 포함한다.
* 예약 시간은 `HH:mm` 형식으로 응답한다.

---

## 기능 목록

### 어드민 메인 페이지

* [x] `/` 요청 처리
* [x] `home.html` 응답

### 예약 관리 페이지

* [x] `/reservation` 요청 처리
* [x] `reservation.html` 응답
* [x] 예약 관리 페이지에서 예약 목록 조회

### 예약 목록 조회

* [x] `/reservations` 요청 처리
* [x] 임의의 예약 데이터 관리
* [x] 예약 목록을 JSON 형식으로 응답
* [x] 예약 도메인 객체와 응답 DTO 분리
* [x] 예약 시간을 `HH:mm` 형식으로 응답

---

## API

### 예약 목록 조회

* Method: `GET`
* URL: `/reservations`
* Response: `200 OK`

**Response Body**

```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2023-01-01",
    "time": "10:00"
  },
  {
    "id": 2,
    "name": "브라운",
    "date": "2023-01-02",
    "time": "11:00"
  }
]
```

---

## 주요 객체

| 객체                      | 역할                             |
| ----------------------- | ------------------------------ |
| `PageController`  | 어드민 메인 페이지 요청을 처리한다.           |
| `ReservationController` | 예약 관리 페이지와 예약 목록 조회 요청을 처리한다.  |
| `Reservation`           | 예약의 식별자, 예약자 이름, 날짜, 시간을 관리한다. |
| `ReservationResponse`   | 예약 정보를 API 응답 형식으로 전달하는 DTO이다. |

---

## 테스트

### MissionStepTest

* `/` 요청 시 `200 OK` 응답 확인
* `/reservation` 요청 시 `200 OK` 응답 확인
* `/reservations` 요청 시 `200 OK` 응답 확인
* `/reservations` 응답의 예약 목록 개수 확인
