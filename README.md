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
