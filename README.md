# roomescape

방탈출 게임 예약을 관리하는 어드민 웹 애플리케이션입니다.

## 실행 방법

### 서버 실행

```bash
./gradlew bootRun
```

서버 실행 후 `http://localhost:8080`으로 접속합니다.


### 테스트 실행

```bash
./gradlew test
```

## 데이터베이스

H2 인메모리(In-Memory) 데이터베이스를 사용합니다. 데이터가 메모리에만 저장되므로 **서버를 종료하면 모든 데이터가 사라지며**, 서버를 시작할 때마다 `schema.sql`이 실행되어 테이블이 새로 생성됩니다.

### H2 콘솔 접속

서버 실행 후 `http://localhost:8080/h2-console`로 접속합니다.

| 항목 | 값 |
|---|---|
| JDBC URL | `jdbc:h2:mem:database` |
| User Name | `sa` |
| Password | (없음) |

## 기능 목록

### 1단계 - 홈 화면

- [x] `GET /` 요청 시 200 응답
- [x] `GET /` 요청 시 `templates/home.html` 렌더링

### 2단계 - 예약 조회

- [x] `GET /reservation` 요청 시 200 응답
- [x] `GET /reservation` 요청 시 `templates/reservation.html` 렌더링
- [x] `GET /reservations` 요청 시 200 응답
- [x] `GET /reservations` 요청 시 `Content-Type: application/json` 응답
- [x] `GET /reservations` 요청 시 예약 목록을 JSON 배열로 응답

### 3단계 - 예약 추가 / 취소

- [x] `POST /reservations` 요청 시 201 응답
- [x] `POST /reservations` 요청 시 `Location` 헤더에 생성된 예약의 경로 응답
- [x] `POST /reservations` 요청 시 생성된 예약 정보를 JSON으로 응답
- [x] `DELETE /reservations/{id}` 요청 시 204 응답
- [x] `DELETE /reservations/{id}` 요청 시 해당 예약이 목록에서 제거됨

### 4단계 - 예외 처리

- [x] `POST /reservations` 요청 시 이름이 비어있으면 400 응답
- [x] `POST /reservations` 요청 시 날짜가 비어있으면 400 응답
- [x] `POST /reservations` 요청 시 시간이 비어있으면 400 응답
- [x] `DELETE /reservations/{id}` 요청 시 존재하지 않는 예약이면 404 응답

### 5단계 - 데이터베이스 적용

- [x] H2 데이터베이스 의존성 추가 (`spring-boot-starter-jdbc`, `h2`)
- [x] `schema.sql`로 `reservation` 테이블 정의
- [x] datasource url을 `jdbc:h2:mem:database`로 지정
- [x] H2 콘솔 활성화

> 이 단계에서는 데이터베이스 연결과 테이블 생성까지만 다루며, 예약 데이터는 아직 애플리케이션 메모리(`List`)에 저장됩니다.

### 6단계 - 데이터 조회하기

- [x] `GET /reservations` 요청 시 메모리가 아닌 데이터베이스에서 예약 목록을 조회
- [x] `JdbcTemplate`과 `RowMapper`를 사용하는 `ReservationRepository` 추가

> 조회만 데이터베이스로 전환된 단계로, 추가/취소는 아직 메모리를 사용합니다. 두 저장소가 분리되어 있어 예약 관리 기능은 정상 동작하지 않습니다.

### 7단계 - 데이터 추가/삭제하기

- [x] `POST /reservations` 요청 시 데이터베이스에 예약을 저장하고, DB가 생성한 id로 응답
- [x] `DELETE /reservations/{id}` 요청 시 데이터베이스에서 예약을 삭제
- [x] `DELETE /reservations/{id}` 요청 시 삭제된 행이 없으면 404 응답
- [x] 메모리 저장소(`List`)와 식별자 생성기(`AtomicLong`) 제거

> 식별자 생성은 `AtomicLong` 대신 데이터베이스의 `AUTO_INCREMENT`에 위임합니다.

### 추가 구현 (미션 요구사항 외)

- [x] `POST /reservations` 요청 시 현재 시각보다 과거의 날짜/시간으로 예약하면 400 응답

## API 명세

### 예약 목록 조회

**Request**

```
GET /reservations HTTP/1.1
```

**Response**

```
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
    },
    {
        "id": 3,
        "name": "브라운",
        "date": "2023-01-03",
        "time": "12:00"
    }
]
```

### 예약 추가

**Request**

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "name": "브라운",
    "date": "2030-08-05",
    "time": "15:40"
}
```

**Response**

```
HTTP/1.1 201
Location: /reservations/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2030-08-05",
    "time": "15:40"
}
```

> `id`는 데이터베이스의 `AUTO_INCREMENT`가 생성합니다.

### 예약 취소

**Request**

```
DELETE /reservations/1 HTTP/1.1
```

**Response**

```
HTTP/1.1 204 No Content
```

### 예약 추가 실패 (필수 값 누락)

**Request**

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "name": "브라운",
    "date": "",
    "time": ""
}
```

**Response**

```
HTTP/1.1 400 Bad Request
```

### 예약 취소 실패 (존재하지 않는 예약)

**Request**

```
DELETE /reservations/999 HTTP/1.1
```

**Response**

```
HTTP/1.1 404 Not Found
```

### 예약 추가 실패 (과거 시간)

미션 요구사항에는 없지만, 현재 시각보다 과거의 날짜/시간으로는 예약할 수 없도록 직접 추가했습니다.

**Request**

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "name": "브라운",
    "date": "2020-01-01",
    "time": "10:00"
}
```

**Response**

```
HTTP/1.1 400 Bad Request
```
