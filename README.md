# 방탈출 관리 애플리케이션

# 기능 명세

### HTML 페이지 표시

- `GET /`
- `GET /reservation`

### API 공통

**입력을 파싱하지 못할 경우 응답**

```http request
HTTP 400
Content-Type: application/json

{
    "type": "InvalidFormatException",
    "message": "JSON 파싱에 실패했습니다.",
    "fields": [
        {
            "field": "time",
            "type": "InvalidFormatException", // 해당 예외 이름
            "message": "line: 4, column: 11에서 오류가 발생했습니다."
        }
    ]
}
```

**입력 형식이 잘못될 경우 응답**

```http request
HTTP 400
Content-Type: application/json

{
    "type": "MalformedInput",
    "message": "입력 형식이 잘못되었습니다.",
    "fields": [
        {
            "field": "name",
            "type": "Length",
            "message": "길이가 0에서 6 사이여야 합니다."
        }
    ]
}
```

**내부적인 오류가 발생할 경우 응답**

```http request
HTTP 500
Content-Type: application/json

{
    "type": "InternalError",
    "message": "내부 오류가 발생했습니다. 개발자에게 문의해주세요.",
    "errorCode": "2026-05-10:kF8M:8a"
}
```

이 경우, 백엔드 서버 로그에서 해당 오류코드를 검색하면 상세한 오류 로그를 확인할 수 있습니다.

### `GET /reservations`

전체 예약 목록을 반환합니다.

> `() -> ReservationDto[]`

**정상 응답**

```http request
HTTP 200
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

새로운 예약을 추가합니다.

> `(body: CreateReservationBody) -> ReservationDto`

- 이름은 최대 20자만 가능하고, 한글, 영어 등의 문자만 가능합니다. (Unicode Letter Category)
- 기존 예약과 중복되는 시간에 예약할 수 없습니다.

**정상 요청**

```http request
POST /reservations
Content-Type: application/json

{
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

**정상 응답**

```http request
HTTP 201 Created
Location: /reservations/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

**해당 시간에 중복되는 예약이 있을 경우 응답**

```http request
HTTP 400 
Content-Type: application/json

{
  "type": "Reservation.DuplicateTime",
  "message": "해당 예약 시간에 이미 다른 예약이 있습니다."
}
```

### `DELETE /reservations/{id}`

해당 id를 가진 예약을 삭제합니다.

> `(pathId: ReservationId) -> void`

**정상 응답**

```http request
HTTP 204 No Content
```
