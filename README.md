# 방탈출 관리 애플리케이션

## 기능 요구사항

### `GET /`

어드민 메인 페이지 표시

### `GET /reservation`

예약 페이지 표시

### `GET /reservations`

전체 예약 목록 표시

**Response**
```http request
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

### `POST /reservations`

예약 추가

**Request**
```http request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

**Response**
```http request
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

### `DELETE /reservations/{id}`

예약 삭제

**Response**
```http request
HTTP/1.1 204 No Content
```
