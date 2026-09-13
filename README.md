# 1단계 - 홈 화면

## 기능 명세

- 사용자는 `/`에 접속하여 홈 화면을 확인할 수 있다
- 홈 화면에는 예약 관리와 관련된 메뉴가 표시된다
- Spring MVC를 이용하여 사용자의 요청에 맞는 화면을 반환한다

## API 명세

### 홈 화면 조회

- Method: `GET`
- URL: `/`
- 설명: 애플리케이션의 홈 화면을 조회한다
- 성공 상태 코드: `200 OK`
- Response: `templates/home.html`을 화면으로 반환한다


# 2단계 - 예약 조회

## 기능 명세

- 사용자는 `/reservation`에 접속하여 예약 관리 화면을 확인할 수 있다
- 예약 관리 화면에서 현재 등록되어 있는 예약 목록을 확인할 수 있다
- 예약 목록은 예약 번호, 예약자, 날짜, 시간 등의 정보를 포함한다
- 예약 생성 기능은 아직 구현하지 않고, 학습을 위해 미리 생성한 예약 데이터를 조회한다

## API 명세

### 예약 목록 조회

- Method: `GET`
- URL: `/reservations`
- 설명: 현재 저장되어 있는 전체 예약 목록을 조회한다
- 성공 상태 코드: `200 OK`
- Response Content-Type: `application/json`
- Response Body: 예약 목록을 JSON 형태로 반환한다

## 역할 분리

### Controller
- HTTP 요청을 받는다
- Repository에 예약 목록 조회를 요청한다
- 조회한 예약 목록을 HTTP 응답으로 반환한다

### ReservationRepository
- 예약 데이터를 저장한다
- 저장된 예약 목록을 조회한다

### Reservation
- 예약 정보를 표현하는 객체이다


# 8단계 - 예약 시간 관리

## 기능 명세

- 사용자는 `/time`에 접속하여 시간 관리 화면을 확인할 수 있다
- 예약 가능한 시간을 등록하고 목록을 조회하거나 삭제할 수 있다
- 시간은 `HH:mm` 형식으로 입력한다
- 예약 시간은 `reservation_time` 테이블에 저장한다

## API 명세

### 시간 관리 화면 조회

- Method: `GET`
- URL: `/time`
- 설명: 예약 시간 관리 화면을 조회한다
- 성공 상태 코드: `200 OK`
- Response: `templates/time.html`을 화면으로 반환한다

### 예약 시간 등록

- Method: `POST`
- URL: `/times`
- 설명: 새로운 예약 시간을 등록한다
- Request Content-Type: `application/json`
- Request Body: `{"time": "10:00"}`
- 성공 상태 코드: `201 Created`
- Location: `/times/{id}`
- Response Body: `{"id": 1, "time": "10:00"}`
- 실패 상태 코드: 시간 값이 비어 있거나 형식이 잘못되면 `400 Bad Request`

### 예약 시간 목록 조회

- Method: `GET`
- URL: `/times`
- 설명: 저장된 전체 예약 시간을 조회한다
- 성공 상태 코드: `200 OK`
- Response Content-Type: `application/json`
- Response Body: `[{"id": 1, "time": "10:00"}]`

### 예약 시간 삭제

- Method: `DELETE`
- URL: `/times/{id}`
- 설명: 해당 ID의 예약 시간을 삭제한다
- 성공 상태 코드: `204 No Content`
- 실패 상태 코드: 시간이 존재하지 않으면 `404 Not Found`

## 역할 분리

### TimeController

- 시간 관련 HTTP 요청을 받는다
- TimeService에 처리를 요청하고 HTTP 응답을 반환한다

### TimeService

- 예약 시간 조회, 등록, 삭제를 처리한다
- 삭제할 시간이 존재하지 않으면 예외를 발생시킨다

### TimeRepository

- JdbcTemplate을 사용하여 예약 시간을 조회, 저장, 삭제한다

### ReservationTime

- 예약 시간의 ID와 시간 값을 표현하는 객체이다


# 9단계 - 예약과 시간 연결

## 기능 명세

- 사용자는 예약할 때 미리 등록된 시간을 선택한다
- 예약 요청의 `time`에는 시간 문자열 대신 예약 시간의 ID를 전달한다
- 예약 테이블의 `time_id`가 예약 시간 테이블의 ID를 참조한다
- 예약 조회 결과에는 예약 시간의 ID와 시간 값이 함께 포함된다
- 존재하지 않는 시간이나 현재 또는 과거 시각으로 예약할 수 없다
- 예약에서 사용 중인 시간은 삭제할 수 없다

## API 명세

### 예약 등록

- Method: `POST`
- URL: `/reservations`
- 설명: 등록된 시간 ID를 사용하여 예약을 생성한다
- Request Content-Type: `application/json`
- Request Body: 아래 예시에서 `time`은 미리 등록한 시간의 ID이다

```json
{
  "name": "브라운",
  "date": "2099-08-05",
  "time": 1
}
```

- 성공 상태 코드: `201 Created`
- Location: `/reservations/{id}`
- Response Body:

```json
{
  "id": 1,
  "name": "브라운",
  "date": "2099-08-05",
  "time": {
    "id": 1,
    "time": "10:00"
  }
}
```

- 실패 상태 코드: 기존 문자열 시간 요청, 존재하지 않는 시간 ID, 잘못된 예약 정보는 `400 Bad Request`

### 예약 목록 조회

- Method: `GET`
- URL: `/reservations`
- 설명: 예약 정보와 연결된 시간 정보를 함께 조회한다
- 성공 상태 코드: `200 OK`
- Response Content-Type: `application/json`
- Response Body: 예약 등록 응답과 같은 구조의 객체를 JSON 배열로 반환한다

### 예약 수정

- Method: `PUT`
- URL: `/reservations/{id}`
- 설명: 예약자, 날짜, 예약 시간 ID를 변경한다
- Request Body: 예약 등록 요청과 같은 형식을 사용한다
- 성공 상태 코드: `200 OK`
- Response Body: 수정된 예약 정보와 연결된 시간 정보를 반환한다
- 실패 상태 코드: 잘못된 예약 정보는 `400 Bad Request`, 유효한 요청에서 예약이 존재하지 않으면 `404 Not Found`

### 사용 중인 예약 시간 삭제 제한

- Method: `DELETE`
- URL: `/times/{id}`
- 설명: 예약에서 참조하는 시간은 삭제하지 않는다
- 실패 상태 코드: `409 Conflict`

## 역할 분리

### ReservationRequest

- 예약자, 날짜, 예약 시간 ID를 입력받는다

### ReservationService

- 시간 ID로 등록된 예약 시간을 찾는다
- 예약 날짜와 시간을 검증한 뒤 저장 또는 수정을 요청한다

### ReservationRepository

- 예약에 시간 ID를 저장한다
- JOIN을 사용하여 예약과 시간 정보를 함께 조회한다

### ReservationResponse

- 예약 정보와 연결된 시간 객체를 응답으로 전달한다


# 10단계 - 데이터베이스 접근 로직 분리

## 기능 명세

- Controller는 HTTP 요청과 응답 처리를 담당한다
- Service는 예약 검증과 처리 흐름을 담당한다
- Repository는 데이터베이스 접근을 담당한다
- ReservationController는 JdbcTemplate을 직접 사용하지 않는다
- 기존에 분리된 구조를 유지하고 테스트로 역할 분리를 확인한다

## API 명세

- 새로운 API를 추가하지 않는다
- 기존 예약 및 시간 관리 API의 요청과 응답을 유지한다

## 역할 분리

### ReservationController

- HTTP 요청을 받고 ReservationService에 처리를 요청한다
- 처리 결과를 HTTP 응답으로 반환한다

### ReservationService

- 예약 시간 조회와 예약 날짜 검증을 수행한다
- ReservationRepository에 데이터 조회 및 변경을 요청한다

### ReservationRepository

- JdbcTemplate을 사용하여 SQL을 실행한다
- 데이터베이스 조회 결과를 Reservation 객체로 변환한다

## 검증

- ReservationController에 JdbcTemplate 타입의 필드가 없는지 확인한다
- 기존 예약 및 시간 관리 테스트가 정상적으로 통과하는지 확인한다
