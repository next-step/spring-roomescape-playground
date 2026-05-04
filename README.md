# Reservation
___

## Request
```GET /reservations HTTP/1.1```

## Response
```HTTP/1.1 200
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
___

## 예약 추가 Request
```POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

## 예약 추가 Response
```HTTP/1.1 201 
Location: /reservations/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```
## 예약 취소 Request
```
DELETE /reservations/1 HTTP/1.1
```

## 예약 취소 Response
```
HTTP/1.1 204 No Content
```

## 프로그래밍 요구사항

- [x] localhost:8080 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 templates/home.html 파일을 이용하세요.
- [x] /reservation 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 templates/reservation.html 파일을 이용하세요.
- [x] 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.
- [x] API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- [x] 아래 화면에서 예약 추가와 취소가 잘 동작해야합니다.

## 클래스 정리

### Reservation

예약 정보를 저장한다.

#### toEntity

Reservation과 id를 받았을때 Reservation 관련 정보(id, name, date, time)을 반환한다.

### RoomescapeController

#### showHomePage
home.html 화면을 보여준다.

#### showReservationPage
reservation.html 화면을 보여준다.

#### getReservations
전체 예약 목록을 반환한다.

#### addReservation
예약을 추가하고, 생성된 예약을 반환한다.
- 생성된 예약의 URI: /reservations/{id}

#### deleteReservation
id에 해당하는 예약을 삭제한다.
- 해당 id의 예약이 없으면 RuntimeException을 던진다.

## 예외 처리
해당 id의 예약이 없을 때
