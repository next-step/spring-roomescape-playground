## 📂 프로젝트 구조

```
.
├── build.gradle
├── src
│   ├── main
│   │   ├── java
│   │   │   └── roomescape
│   │   │       ├── controller
│   │   │       │   ├── AdminController.java      # 웹 페이지 라우팅
│   │   │       │   └── ReservationController.java # 예약 API
│   │   │       ├── dto                         # 데이터 전송 객체
│   │   │       ├── model                       # 데이터 모델
│   │   │       ├── repository                  # 데이터 접근
│   │   │       └── service                     # 비즈니스 로직
```

## 📝 API 엔드포인트

| Method | URL | 설명 |
| --- | --- | --- |
| `GET` | `/reservations` | 모든 예약을 조회합니다. |
| `POST` | `/reservations` | 새로운 예약을 생성합니다. |
| `DELETE` | `/reservations/{id}` | 특정 ID의 예약을 삭제합니다. |

### `POST /reservations` 요청 예시

**Request Body:**
```json
{
  "name": "woowahan",
  "date": "2025-11-13",
  "time": "14:00"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "woowahan",
  "date": "2025-11-13",
  "time": "14:00"
}
```
