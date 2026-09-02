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

# 3단계 - 예약 추가 / 취소

## 기능 명세서

* 예약을 추가할 수 있다.
* 예약 번호는 서버에서 자동으로 생성한다.
* 등록된 예약 목록을 조회할 수 있다.
* 예약 번호를 이용해 예약을 삭제할 수 있다.
* 예약 추가 성공 시 `201 Created`를 반환한다.
* 예약 삭제 성공 시 `204 No Content`를 반환한다.

## API 명세서

### 예약 조회

```http
GET /reservations
```

응답

```http
200 OK
```

### 예약 추가

```http
POST /reservations
Content-Type: application/json
```

요청 예시

```json
{
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

응답

```http
201 Created
Location: /reservations/1
```

응답 예시

```json
{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

### 예약 삭제

```http
DELETE /reservations/{id}
```

응답

```http
204 No Content
```

# 4단계 - 예외 처리

## 기능 명세

- 예약 추가 시 이름, 날짜, 시간이 비어있는지 검증한다.
- 잘못된 예약 정보가 요청되면 `400 Bad Request`를 반환한다.
- 존재하지 않는 예약을 삭제하려는 경우 `404 Not Found`를 반환한다.
- 예외 발생 시 `ErrorResponse`를 통해 오류 메시지를 응답한다.
- 예외 처리는 `@ControllerAdvice`와 `@ExceptionHandler`를 이용한다.

## API 명세서

### 잘못된 예약 추가

```http
POST /reservations
Content-Type: application/json
```

요청 예시

```json
{
  "name": "",
  "date": "",
  "time": ""
}
```

응답

```http
400 Bad Request
Content-Type: application/json
```

응답 예시

```json
{
  "message": "예약 정보가 올바르지 않습니다."
}
```

### 존재하지 않는 예약 삭제

```http
DELETE /reservations/{id}
```

응답

```http
404 Not Found
Content-Type: application/json
```

응답 예시

```json
{
  "message": "예약을 찾을 수 없습니다."
}
```

# 5단계 - 데이터베이스 적용하기

## 기능 명세

- H2 인메모리 데이터베이스를 사용하여 예약 데이터를 저장한다
- `JdbcTemplate`을 이용하여 데이터베이스에 접근한다
- 예약 생성 시 `reservation` 테이블에 예약 정보를 저장한다
- 예약 목록 조회 시 데이터베이스에 저장된 예약 정보를 조회한다
- 예약 수정 시 해당 예약 정보를 데이터베이스에서 수정한다
- 예약 삭제 시 해당 예약 정보를 데이터베이스에서 삭제한다
- 존재하지 않는 예약을 수정하거나 삭제할 경우 예외를 발생시킨다

## 데이터베이스 명세

### 예약 테이블

- Table: `reservation`
- `id`: 예약 번호, `BIGINT`, 자동 증가, 기본키
- `name`: 예약자 이름, `VARCHAR(255)`, NOT NULL
- `date`: 예약 날짜, `VARCHAR(255)`, NOT NULL
- `time`: 예약 시간, `VARCHAR(255)`, NOT NULL

## 데이터베이스 설정

- Database: H2
- JDBC URL: `jdbc:h2:mem:database`
- H2 Console을 활성화하여 데이터베이스 상태를 확인할 수 있다
- `schema.sql`을 이용하여 애플리케이션 실행 시 예약 테이블을 생성한다

## API 명세

### 예약 목록 조회

- Method: `GET`
- URL: `/reservations`
- 설명: 데이터베이스에 저장된 전체 예약 목록을 조회한다
- 성공 상태 코드: `200 OK`

### 예약 생성

- Method: `POST`
- URL: `/reservations`
- 설명: 새로운 예약 정보를 데이터베이스에 저장한다
- 성공 상태 코드: `200 OK`
- Request:
  - `name`: 예약자 이름
  - `date`: 예약 날짜
  - `time`: 예약 시간

### 예약 수정

- Method: `PUT`
- URL: `/reservations/{id}`
- 설명: 예약 번호에 해당하는 예약 정보를 수정한다
- 성공 상태 코드: `200 OK`
- 예약이 존재하지 않는 경우: `404 Not Found`

### 예약 삭제

- Method: `DELETE`
- URL: `/reservations/{id}`
- 설명: 예약 번호에 해당하는 예약 정보를 삭제한다
- 성공 상태 코드: `200 OK`
- 예약이 존재하지 않는 경우: `404 Not Found`
