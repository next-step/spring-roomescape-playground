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

### 🛡️ 4단계 추가 구현 사항 (예외 처리 및 보안 강화)

클라이언트의 비정상적인 요청과 악의적인 접근으로부터 서버를 보호하기 위해 `GlobalExceptionHandler`를 도입하여 아래와 같은 예외 상황을 전역에서 400 Bad Request로 통합 처리했습니다.

1. **데이터 무결성 및 비즈니스 룰 검증**
   - **필수 값 누락**: 이름, 날짜, 시간이 비어있는 요청 차단 (`IllegalArgumentException`)
   - **중복 예약 방지**: 동일한 날짜/시간에 중복 예약 시도 차단 (`DuplicateReservationException`)
   - **과거 예약 방지**: 현재 시간 이전의 과거 날짜로 예약 시도 차단 (`PastDateReservationException`)
   - **잘못된 데이터 형식**: 이름 길이 제한 등 정해진 규격을 벗어난 데이터 요청 차단 (`InvalidReservationDataException`)

2. **존재하지 않는 리소스 접근 방어**
   - 예약 단건 조회(GET) 및 삭제(DELETE) 요청 시 대상 ID 존재 여부 우선 확인 (`NotFoundReservationException`)

3. **타입 불일치 및 포맷 오류 방어 (Spring 내장 예외 활용)**
   - URL 경로 변수 타입 오류 (예: 숫자가 들어가야 할 경로에 문자가 들어온 경우) (`MethodArgumentTypeMismatchException`)
   - JSON 포맷 및 데이터 파싱 오류 차단 (`HttpMessageNotReadableException`)

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
