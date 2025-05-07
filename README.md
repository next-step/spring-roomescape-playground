# 📘 Reservation API 명세서

예약 생성, 조회, 삭제 기능이 있으며 
잘못된 요청 발생 시 400 BadRequest로 응답합니다.

---

##  1.예약자 조회

**GET** `/reservations`

### ✅ Response

- **200 OK**
- **Body**
```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
  }
]
```

---

## 2. 예약 추가

**POST** `/reservations`

### ✅ Request
```http
Content-Type: application/json
```

```json
{
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

### ✅ Response

- **201 Created**
- **Location Header**: `/reservations/1`
- **Body**
```json
{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

### ❌ 예외 상황 (400 Bad Request)

| 조건                             | 설명                        |
|----------------------------------|-----------------------------|
| name이 null 또는 공백            | 예약자 이름 누락            |
| date 또는 time이 null            | 날짜/시간 누락              |
| 날짜가 과거일 경우              | 과거 예약 불가              |
| 동일한 name+date+time 조합 존재 | 중복 예약 금지              |
---

## 🔹 3. 예약 삭제

**DELETE** `/reservations/{id}`

### ✅ Path Parameter

- `id` : 예약 ID (정수)

### ✅ Response

- **204 No Content**

### ❌ 예외 상황 (400 Bad Request)

| 조건                  | 설명                       |
|-----------------------|----------------------------|
| 존재하지 않는 id 삭제 | 해당 ID의 예약이 없음      |
| id가 0 이하인 경우    | 유효하지 않은 식별자       |

---

## 🔧 에러 응답 예시

```http
HTTP/1.1 400 Bad Request
```
