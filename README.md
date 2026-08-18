# 방 탈출 예약 관리 - Spring MVC (1~4단계)

## 미션 소개

Spring MVC를 활용하여 방탈출 어드민 페이지를 구성하고,
예약 조회, 생성, 삭제 기능을 제공하는 예약 관리 API를 구현하는 프로젝트

---

## 요구사항

* Spring MVC를 활용하여 웹 요청을 처리한다.
* Thymeleaf 템플릿 엔진을 활용하여 화면을 응답한다.
* `/` 요청 시 어드민 메인 페이지를 응답한다.
* `/reservation` 요청 시 예약 관리 페이지를 응답한다.
* 예약 목록을 조회할 수 있다.
* 새로운 예약을 생성할 수 있다.
* 기존 예약을 삭제할 수 있다.
* 별도의 데이터베이스 없이 예약 목록을 관리한다.
* 예약 조회 응답에는 `id`, `name`, `date`, `time` 정보를 포함한다.
* 예약 시간은 `HH:mm` 형식으로 응답한다.

---

## 예약 정책

미션 요구사항을 구현하는 과정에서 다음과 같은 예약 정책을 추가로 적용하였다.

* 현재 시각 이전의 예약은 생성할 수 없다.
* 현재와 같은 분의 예약은 생성할 수 없다.
* 동일한 이름, 날짜, 시간의 중복 예약은 생성할 수 없다.
* 존재하지 않는 예약은 삭제할 수 없다.

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

* [x] `GET /reservations` 요청 처리
* [x] 예약 목록을 JSON 형식으로 응답
* [x] 예약 도메인 객체와 응답 DTO 분리
* [x] 예약 시간을 `HH:mm` 형식으로 응답

### 예약 생성

* [x] `POST /reservations` 요청 처리
* [x] 예약 ID 생성
* [x] 생성된 예약 정보 응답
* [x] `201 Created` 응답
* [x] `Location` 헤더 응답
* [x] 현재 시각 이전 예약 검증
* [x] 중복 예약 검증

### 예약 삭제

* [x] `DELETE /reservations/{id}` 요청 처리
* [x] 예약 ID를 기준으로 삭제
* [x] 정상 삭제 시 `204 No Content` 응답
* [x] 존재하지 않는 예약 삭제 검증

### 예외 처리

* [x] 잘못된 예약 요청 시 `400 Bad Request` 응답
* [x] 존재하지 않는 예약 요청 시 `404 Not Found` 응답
* [x] 중복 예약 요청 시 `409 Conflict` 응답

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
    "date": "2026-08-20",
    "time": "10:00"
  },
  {
    "id": 2,
    "name": "브라운",
    "date": "2026-08-21",
    "time": "11:00"
  }
]
```

---

### 예약 생성

* Method: `POST`
* URL: `/reservations`
* Response: `201 Created`

**Request Body**

```json
{
  "name": "브라운",
  "date": "2026-08-20",
  "time": "15:40"
}
```

**Response Header**

```text
Location: /reservations/4
```

**Response Body**

```json
{
  "id": 4,
  "name": "브라운",
  "date": "2026-08-20",
  "time": "15:40"
}
```

---

### 예약 삭제

* Method: `DELETE`
* URL: `/reservations/{id}`
* Response: `204 No Content`

---

## 주요 객체

| 객체 | 역할 |
| --- | --- |
| `PageController` | 어드민 페이지 요청을 처리하고 View를 반환한다. |
| `ReservationController` | 예약 조회, 생성, 삭제 API 요청을 처리한다. |
| `ReservationService` | 예약 생성 및 삭제 과정의 비즈니스 로직을 처리한다. |
| `ReservationRepository` | 예약 데이터를 저장, 조회, 삭제한다. |
| `Reservation` | 예약의 식별자, 예약자 이름, 날짜, 시간을 관리한다. |
| `ReservationRequest` | 예약 생성 요청 데이터를 전달하는 DTO이다. |
| `ReservationResponse` | 예약 정보를 API 응답 형식으로 전달하는 DTO이다. |
| `GlobalExceptionHandler` | 예약 처리 중 발생한 예외를 HTTP 응답으로 변환한다. |

---

## 예외 처리

| 상황 | HTTP Status |
| --- | --- |
| 잘못된 예약 요청 | `400 Bad Request` |
| 존재하지 않는 예약 | `404 Not Found` |
| 중복 예약 | `409 Conflict` |

---

## 테스트

### MissionStepTest

* `/` 요청 시 `200 OK` 응답 확인
* `/reservation` 요청 시 `200 OK` 응답 확인
* 예약 목록 조회 확인
* 예약 생성 시 `201 Created` 응답 확인
* 예약 삭제 시 `204 No Content` 응답 확인

### ReservationControllerTest

* 예약 목록 조회 확인
* 예약 시간 `HH:mm` 형식 확인
* 예약 생성 API 응답 확인
* 예약 삭제 API 응답 확인

### ReservationServiceTest

* 과거 날짜 예약 검증
* 현재보다 이전 시간 예약 검증
* 현재와 같은 분의 예약 검증
* 현재보다 이후 시간의 예약 생성 확인
* 중복 예약 검증
* 존재하는 예약 삭제 확인
* 존재하지 않는 예약 삭제 검증

### ReservationRepositoryTest

* 예약 목록 조회 확인
* 예약 저장 확인
* 동일한 이름, 날짜, 시간의 예약 존재 여부 확인
* 예약 삭제 확인
