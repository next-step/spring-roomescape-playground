# roomescape

방탈출 게임 예약을 관리하는 어드민 웹 애플리케이션입니다.

## 실행 방법

### 서버 실행

```bash
./gradlew bootRun
```

서버 실행 후 `http://localhost:8080`으로 접속합니다.


### 테스트 실행

```bash
./gradlew test
```

## 기능 목록

### 1단계 - 홈 화면

- [x] `GET /` 요청 시 200 응답
- [x] `GET /` 요청 시 `templates/home.html` 렌더링

### 2단계 - 예약 조회

- [x] `GET /reservation` 요청 시 200 응답
- [x] `GET /reservation` 요청 시 `templates/reservation.html` 렌더링
- [x] `GET /reservations` 요청 시 200 응답
- [x] `GET /reservations` 요청 시 `Content-Type: application/json` 응답
- [x] `GET /reservations` 요청 시 예약 목록을 JSON 배열로 응답

### 3단계 - 예약 추가 / 취소

- [x] `POST /reservations` 요청 시 201 응답
- [x] `POST /reservations` 요청 시 `Location` 헤더에 생성된 예약의 경로 응답
- [x] `POST /reservations` 요청 시 생성된 예약 정보를 JSON으로 응답
- [x] `DELETE /reservations/{id}` 요청 시 204 응답
- [x] `DELETE /reservations/{id}` 요청 시 해당 예약이 목록에서 제거됨

## API 명세

### 예약 목록 조회

**Request**

```
GET /reservations HTTP/1.1
```

**Response**

```
HTTP/1.1 200
Content-Type: application/json

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
    },
    {
        "id": 3,
        "name": "브라운",
        "date": "2023-01-03",
        "time": "12:00"
    }
]
```

### 예약 추가

**Request**

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

**Response**

```
HTTP/1.1 201
Location: /reservations/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

### 예약 취소

**Request**

```
DELETE /reservations/1 HTTP/1.1
```

**Response**

```
HTTP/1.1 204 No Content
```
