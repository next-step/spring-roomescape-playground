# 방탈출 어드민 애플리케이션

### 1단계 요구사항
- [x] 웹 관련 gradle 의존성 추가
- [x] localhost:8080 요청 시 templates/home.html 파일 응답

### 2단계 요구사항
- [ ] /reservation 요청 시 templates/reservation.html 파일 응답
- [ ] 예약 목록 조회 API 구현

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
