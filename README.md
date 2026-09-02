# 스프링 MVC 

## 기능 명세서
- [x] 사용자는 홈 화면에 접속할 수 있다.
- [x] 사용자는 예약 페이지에 접속할 수 있다.
- [x] 사용자는 저장된 예약 목록을 조회할 수 있다.
- [x] 사용자가 직접 예약을 신청 및 취소를 할 수 있다.
- [x] 사용자는 필수 입력값을 모두 입력해야만 예약을 신청할 수 있다.
- [x] 사용자는 시간 관리 페이지에 접속할 수 있다.
- [x] 사용자는 저장된 예약 시간 목록을 조회할 수 있다.
- [x] 사용자가 직접 예약 시간을 추가 및 삭제할 수 있다.
- [x] 사용자는 시간 값을 입력해야만 예약 시간을 추가할 수 있다.
- [x] 사용자는 등록된 예약 시간 중 하나를 선택해 예약을 신청한다.

## API 명세서

| 기능 | Method | URL | Request | Response |
|---|---|---|---|---|
| 홈 화면 | GET | / | - | 200 OK, home.html |
| 예약 화면 | GET | /reservation | - | 200 OK, new-reservation.html |
| 예약 목록 조회 | GET | /reservations | - | 200 OK |
| 예약 생성 | POST | /reservations | Request Body (JSON) | 201 Created, Location : /reservations/{id} |
| 예약 삭제 | DELETE | /reservations/{id} | 삭제하고자 하는 예약의 id | 204 No Content |
| 시간 관리 화면 | GET | /time | - | 200 OK, time.html |
| 예약 시간 목록 조회 | GET | /times | - | 200 OK |
| 예약 시간 생성 | POST | /times | Request Body (JSON) | 201 Created, Location : /times/{id} |
| 예약 시간 삭제 | DELETE | /times/{id} | 삭제하고자 하는 시간의 id | 204 No Content |

### 예약 목록 조회 - Response Body 예시
```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2026-01-01",
    "time": {
      "id": 1,
      "time": "10:00"
    }
  }
]
```

### 예약 생성 - Request Body 예시
`time`은 선택한 예약 시간의 id 이다.
```json
  {
    "name": "브라운",
    "date": "2026-01-01",
    "time": 1
  }
```

### 예약 생성 - Response Body 예시
```json
  {
    "id": 1,
    "name": "브라운",
    "date": "2026-01-01",
    "time": {
      "id": 1,
      "time": "10:00"
    }
  }
```

### 예약 생성 - 실패 응답 (400 Bad Request)
```json
  {
    "message": "이름은 공백이 될 수 없습니다."
  }
```

### 예약 생성 - 실패 응답 (404 Not Found)
존재하지 않는 시간 id로 예약을 신청한 경우.
```json
  {
    "message": "존재하지 않는 예약 시간입니다."
  }
```

### 예약 삭제 - 실패 응답 (404 Not Found)
```json
  {
    "message": "해당 id의 예약이 존재하지 않습니다."
  }
```

### 예약 시간 목록 조회 - Response Body 예시
```json
[
  {
    "id": 1,
    "time": "10:00"
  }
]
```

### 예약 시간 생성 - Request Body 예시
```json
  {
    "time": "10:00"
  }
```

### 예약 시간 생성 - Response Body 예시
```json
  {
    "id"  : 1,
    "time": "10:00"
  }
```

### 예약 시간 생성 - 실패 응답 (400 Bad Request)
```json
  {
    "message": "예약시간은 누락될 수 없습니다."
  }
```

### 예약 시간 삭제 - 실패 응답 (404 Not Found)
```json
  {
    "message": "해당 id의 예약시간이 존재하지 않습니다."
  }
```
