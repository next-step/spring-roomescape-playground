### 컨트롤러 (Controller)
-   `AdminController`: `@Controller`를 사용하여 웹 페이지를 반환합니다. 사용자가 특정 경로로 접속했을 때, 해당하는 HTML 파일을 렌더링하여 보여줍니다.
    -   `/`: `home.html` (홈 페이지)
    -   `/reservation`: `reservation.html` (예약 관리 페이지)
-   `ReservationController`: `@RestController`를 사용하여 RESTful API를 제공합니다. HTTP 요청을 받아 `ReservationService`를 호출하고, 그 결과를 JSON 형태로 반환합니다.
    -   `GET /reservations`: 모든 예약 목록을 조회합니다.
    -   `POST /reservations`: 새로운 예약을 생성합니다.
    -   `DELETE /reservations/{id}`: 특정 예약을 삭제합니다.

### 서비스 (Service)
-   `ReservationService`: 핵심 비즈니스 로직을 담당합니다.
    -   예약 데이터를 `List<Reservation>` 형태로 메모리에 저장하고 관리합니다.
    -   `AtomicLong`을 사용하여 예약 ID를 순차적으로 생성합니다.
    -   주요 메서드:
        -   `getAllReservations()`: 전체 예약 목록을 반환합니다.
        -   `addReservation()`: 새로운 예약을 리스트에 추가하고, 생성된 예약 객체를 반환합니다.
        -   `deleteReservation()`: ID를 기준으로 예약을 찾아 리스트에서 삭제합니다. 존재하지 않는 ID일 경우 `IllegalArgumentException`을 발생시킵니다.

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

## 📄 페이지

- **홈**: `http://localhost:8080/`
- **예약 관리**: `http://localhost:8080/reservation`
