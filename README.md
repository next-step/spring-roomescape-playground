# Spring MVC

> 예시입니다. 클클이화 파이팅 :D

## 1단계 요구사항

- [x] **`localhost:8080`** 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는**`templates/home.html`**파일을 이용하세요.

## 2단계 요구사항

- [x] /reservation 요청 시 아래 화면과 같이 어드민 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 templates/reservation.html 파일을 이용하세요.
- [x] 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

#### Request

```http request
GET /reservations HTTP/1.1
```

```http
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
    }
]

```

## 3단계 요구사항

API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- [ ] 예약 추가 API
- [ ] 예약 삭제 API

### 예약 추가 API

#### Request
```http request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

```

#### Response
```http
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

### 예약 삭제 API

#### Request

```http request
DELETE /reservations/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 204 No Content

```
