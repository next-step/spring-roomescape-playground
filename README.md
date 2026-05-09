# 요구사항

## 1단계 요구사항

- localhost:8080 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- 어드민 메인 페이지는 templates/home.html 파일을 이용하세요.

## 2단계 요구사항

- /reservation 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- 어드민 메인 페이지는 templates/reservation.html 파일을 이용하세요.
- 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

## 3단계 요구사항

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- 아래 화면에서 예약 추가와 취소가 잘 동작해야합니다.

## 4단계 요구사항

- 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400(Bad Request)으로 응답하도록 예외 처리를 구현하세요.
- 예: 예약 추가 시 필요한 인자값이 비어있는 경우, 혹은 삭제할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우 등

### 4단계 추가 구현 및 리팩터링 사항 (객체지향 설계 및 예외/로그 강화)

리뷰 피드백을 바탕으로 시스템의 확장성과 유지보수성을 높이기 위해 아래와 같이 설계를 개선했습니다.

1. **도메인 주도 검증 및 타입 개선**
   - `Reservation` 객체 내부로 데이터 검증 책임을 이동하여 객체의 응집도를 높이고 컨트롤러를 경량화했습니다.
   - 날짜와 시간을 `String` 대신 자바 8의 `LocalDate`, `LocalTime` API로 변경하여 검증(과거 날짜 예약 방지 등) 및 비교 연산의 안전성을 확보했습니다.

2. **방탈출 전용 계층형 커스텀 예외 도입**
   - 최상위 부모 예외인 `RoomescapeException`을 정의하여 프로젝트의 비즈니스 예외를 그룹화했습니다.
   - `InvalidReservationException` (필수값 누락, 과거 날짜 등)
   - `DuplicateReservationException` (중복 예약)
   - `NotFoundReservationException` (존재하지 않는 예약 조회/삭제)

3. **GlobalExceptionHandler를 통한 전역 예외 처리 및 로깅**
   - 단순 콘솔 출력 대신 `SLF4J` 로거를 도입하여 목적에 맞게 로그 레벨(INFO, WARN, ERROR)을 세분화했습니다.
   - 예외 발생 시 내부 에러 로그 노출 없이, RESTful 설계에 맞는 상태 코드(400 Bad Request, 404 Not Found, 409 Conflict)와 명확한 에러 메시지를 응답 본문(Body)에 포함하여 클라이언트의 대처를 용이하게 했습니다.
   - 데이터 타입 오류(`MethodArgumentTypeMismatchException`) 및 JSON 파싱 오류(`HttpMessageNotReadableException`) 등 스프링 내장 예외 방어 로직을 추가했습니다.

---

# API 명세

#### 예약 조회 Request

```http
GET /reservations HTTP/1.1
```

#### 예약 조회 Response

```http
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

#### 예약 추가 Request

```http
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

#### 예약 추가 Response

```http
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

#### 예약 취소 Request

```http
DELETE /reservations/1 HTTP/1.1
```

#### 예약 취소 Response

```http
HTTP/1.1 204 No Content
```