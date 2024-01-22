# 방탈출 어드민 애플리케이션

### 1단계 요구사항
- [x] 웹 관련 gradle 의존성 추가
- [x] localhost:8080 요청 시 templates/home.html 파일 응답

### 2단계 요구사항
- [x] /reservation 요청 시 templates/reservation.html 파일 응답
- [x] 예약 목록 조회 API 구현

### 3단계 요구사항
- [x] 예약 추가 API 구현
- [x] 예약 삭제 API 구현

### 4단계 요구사항
- [x] 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답
  - [x] 예약 추가 시 필요한 인자값이 비어있는 경우
  - [x] 삭제할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우

### 5단계 요구사항
- [x] Spring jdbc Starter 의존성 추가
- [x] 데이터베이스 설정 추가
- [x] 테이블 스키마 정의

### 6단계 요구사항
- [x] 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정

### 7단계 요구사항
- [ ] 예약 추가 API 처리 로직에서 데이터베이스를 활용하도록 수정
- [ ] 예약 취소 API 처리 로직에서 데이터베이스를 활용하도록 수정

---

# API 명세
### 예약 목록 조회
**Request**
```
GET /reservations HTTP/1.1
```
**Response**
```json
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

### 예약 추가
**Request**
```json
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```
**Response**
```json
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

### 예약 삭제
**Request**
```json
DELETE /reservations/1 HTTP/1.1
```
**Response**
```json
HTTP/1.1 204 No Content
```
