# 스프링 MVC 

## 기능 명세서
- [x] 사용자는 홈 화면에 접속할 수 있다.
- [x] 사용자는 예약 페이지에 접속할 수 있다.
- [x] 사용자는 저장된 예약 목록을 조회할 수 있다.
- [x] 사용자가 직접 예약을 신청 및 취소를 할 수 있다.

## API 명세서

### 홈 화면
- Method : GET
- URL : /
- Request : -
- Response : 200 OK, home.html

### 예약 화면
- Method : GET
- URL : /reservation
- Request : -
- Response : 200 OK, reservation.html

### 예약 목록 조회
- Method : GET
- URL : /reservations
- Request : -
- Response : 200 OK

**Response Body 예시**
```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2026-01-01",
    "time": "10:00"
  }
]
```

### 예약 생성
- Method : POST
- URL : /reservations
- Request : Requsest Body (JSON)
- Response : 201 Created, Location : /reservations/{id}

**Request Body 예시**
```json
  {
    "name": "브라운",
    "date": "2026-01-01",
    "time": "10:00"
  }
```

**Response Body 예시**
```json
  {
    "id"  : 1,
    "name": "브라운",
    "date": "2026-01-01",
    "time": "10:00"
  }
```

### 예약 삭제 
- Method : DELETE
- URL : /reservations/{id}
- Request : 삭제하고자 하는 예약의 id
- Response : 204 No Content 