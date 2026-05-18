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
- [x] 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.
- [x] h2 데이터베이스를 활용하여 데이터를 저장하도록 수정하세요.
- [x] 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정하세요.
- [x] 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정하세요.
- [x] 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.

## 클래스 정리

### Reservation

예약 정보를 담당하는 도메인 모델로, 데이터 저장뿐만 아니라 데이터의 유효성 검증을 스스로 수행한다. id 기반의 equals/hashCode를 통해 객체의 식별성을 보장한다.

#### validate
생성 시점에 이름, 날짜, 시간의 누락 여부를 확인하고, 과거 날짜나 이미 지난 시간으로 예약하는 것을 방지한다.

#### toEntity (Static Factory Method)
ReservationRequest 정보와 새로운 id를 받았을 때, 이를 조합하여 비즈니스 로직(validate)이 검증된 완전한 Reservation 엔티티 객체를 생성하고 반환한다.

#### isSameTime
외부에서 데이터를 꺼내지 않고도, 특정 날짜와 시간이 자신의 예약 정보와 일치하는지 확인한다. (Tell, Don't Ask)

### ReservationRequest

클라이언트의 요청 데이터를 담는 DTO로, 엔티티와 정체성을 분리하여 id가 없는 상태의 데이터를 관리한다.
생성 시점에 필수 값 누락 여부를 검사한다.

### ReservationResponse

클라이언트에게 전달할 응답 데이터를 담는 DTO로, @JsonFormat 등을 통해 노출할 데이터의 형식을 제어한다.

### RoomescapeController
JdbcTemplate을 통해 DB와 직접 통신하며 예약 데이터를 관리한다.

#### showHomePage / showReservationPage
각각 home.html, reservation.html 화면을 보여준다.

#### getReservations
DB에서 전체 예약 목록을 조회하여 ReservationResponse로 변환하여 반환한다.

#### addReservation
예약을 추가하고, 생성된 예약 정보를 반환한다.
- validateDuplicate를 호출하여 DB 기준으로 중복 예약을 먼저 확인한다.
- Reservation.toEntity를 통해 객체를 생성하며, 이 과정에서 유효성 검증 실패 시 예외가 발생한다.
- 성공 시 201 Created 코드와 Location 헤더를 포함하여 응답한다.


#### deleteReservation
id에 해당하는 예약을 DB에서 삭제한다.
- jdbcTemplate.update()의 반환값(영향받은 행 수)이 0이면 NotFoundReservationException을 던진다.
전체 예약 목록을 ReservationResponse로 변환하여 반환한다.

#### addReservation
예약을 추가하고, 생성된 예약 정보를 반환한다.
- validateDuplicate를 호출하여 중복 예약을 먼저 확인한다.
- Reservation.toEntity를 통해 객체를 생성하며, 이 과정에서 유효성 검증 실패 시 예외가 발생한다.
- 성공 시 201 Created 코드와 Location 헤더를 포함하여 응답한다.

#### deleteReservation
id에 해당하는 예약을 삭제한다.
- 해당 id의 예약이 리스트에 없으면 NotFoundReservationException을 던진다.
>>>>>>> upstream/mikeylili
- 성공 시 204 No Content를 반환한다.

#### handleException
발생한 커스텀 예외들을 잡아 적절한 HTTP 상태 코드와 함께 에러 메시지를 바디(Body)에 담아 응답한다.

### NotFoundReservationException
요청한 리소스를 찾을 수 없을 때 사용하는 커스텀 Exception
→ 삭제할 예약이 존재하지 않을 때 던지며, 404 Not Found로 응답한다.

### InvalidReservationException
입력된 데이터가 비즈니스 규칙에 어긋나거나 필수 값이 누락되었을 때 사용하는 커스텀 Exception
→ 과거 날짜 예약이나 값 누락 시 던지며, 400 Bad Request로 응답한다.

### ReservationConflictException
서버의 현재 데이터 상태와 충돌이 발생했을 때 사용하는 커스텀 Exception
→ 이미 동일한 시간에 예약이 존재할 때 던지며, 409 Conflict로 응답한다.

## 예외 처리
- 삭제하려는 해당 id의 예약이 존재하지 않을 때 (404)
- 예약 정보 중 필수 값(이름, 날짜, 시간)이 누락되었을 때 (400)
- 과거 날짜나 이미 지난 시간으로 예약을 시도할 때 (400)
- 이미 예약된 시간에 중복 예약을 시도할 때 (409)
