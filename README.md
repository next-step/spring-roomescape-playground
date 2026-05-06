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
- [x] 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.

## 클래스 정리

### Reservation

예약 정보를 담당하는 도메인 모델로, 데이터 저장뿐만 아니라 데이터의 유효성 검증을 스스로 수행한다.

#### validate
생성 시점에 이름, 날짜, 시간의 누락 여부를 확인하고, 과거 날짜나 이미 지난 시간으로 예약하는 것을 방지한다.

#### toEntity
Reservation 정보와 새로운 id를 받았을 때, 이를 조합하여 완전한 Reservation 엔티티 객체를 생성하고 반환한다.

#### isSameTime
외부에서 데이터를 꺼내지 않고도, 특정 날짜와 시간이 자신의 예약 정보와 일치하는지 확인한다. (Tell, Don't Ask)

### RoomescapeController

#### showHomePage
home.html 화면을 보여준다.

#### showReservationPage
reservation.html 화면을 보여준다.

#### getReservations
전체 예약 목록을 반환한다.

#### addReservation
예약을 추가하고, 생성된 예약을 반환한다.
- validateDuplicate를 호출하여 중복 예약을 먼저 확인한다.
- Reservation.toEntity를 통해 객체를 생성하며, 이때 유효하지 않은 값에 대한 예외가 발생할 수 있다.

#### deleteReservation
id에 해당하는 예약을 삭제한다.
- 해당 id의 예약이 없으면 NotFoundReservationException을 던진다.

#### handleException
발생한 커스텀 예외들을 잡아 적절한 HTTP 상태 코드(400, 409 등)와 메시지로 응답한다.

### NotFoundReservationException
요청 대상을 찾을 수 없거나 필수 값이 없을 때 사용하는 커스텀 Exception
→ 삭제할 예약이 존재하지 않거나, 이름·날짜·시간이 누락되었을 때 던진다.

### InvalidReservationException
입력된 예약 정보가 비즈니스 규칙에 어긋날 때 사용하는 커스텀 Exception
→ 과거의 날짜나 이미 지난 시간으로 예약을 시도할 때 던진다.

### ReservationConflictException
서버의 현재 상태와 충돌이 발생했을 때 사용하는 커스텀 Exception
→ 이미 동일한 시간에 예약이 존재하여 중복 예약을 할 수 없을 때 던진다.

## 예외 처리
- 해당 id의 예약이 존재하지 않을 때
- 예약 정보 중 필수 값(이름, 날짜, 시간)이 없을 때
- 과거 날짜나 이미 지난 시간으로 예약을 시도할 때
- 이미 예약된 시간에 중복 예약을 시도할 때
