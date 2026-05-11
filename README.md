# 요구사항

## 1단계 요구사항

- localhost:8080 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- 어드민 메인 페이지는 templates/home.html 파일을 이용하세요.

## 2단계 요구사항

- /reservation 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- 어드민 메인 페이지는 templates/reservation.html 파일을 이용하세요.
- 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

## 3단계 요구사항

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- 아래 화면에서 예약 추가와 취소가 잘 동작해야합니다.

## 4단계 요구사항

- 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.
- 예를 들면 예약 추가 시 필요한 인자값이 비어있는 경우 혹은 삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우가 있습니다.
- +) 저는 예약 하나를 1시간으로 잡고, 겹치지 않도록 처리했습니다.

# API 명세

#### 예약 조회 Request

```http
GET /reservations HTTP/1.1
```

#### 예약 조회 Response

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

#### 예약 추가 Request

```http
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

#### 예약 추가 Response

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

#### 예약 취소 Request

```http
DELETE /reservations/1 HTTP/1.1
```

#### 예약 취소 Response

```http
HTTP/1.1 204 No Content
```
