# roomescape

방탈출 게임 예약과 예약 가능한 시간표를 관리하는 어드민 웹 애플리케이션입니다.

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

통합 테스트는 8080 포트에서 서버를 실행하므로, 테스트 전에 실행 중인 애플리케이션을 종료합니다.

## 데이터베이스

H2 인메모리(In-Memory) 데이터베이스를 사용합니다. 데이터가 메모리에만 저장되므로 **서버를 종료하면 모든 데이터가 사라지며**, 서버를 시작할 때마다 `schema.sql`이 실행되어 테이블이 새로 생성됩니다.

### H2 콘솔 접속

서버 실행 후 `http://localhost:8080/h2-console`로 접속합니다.

| 항목 | 값 |
|---|---|
| JDBC URL | `jdbc:h2:mem:database` |
| User Name | `sa` |
| Password | (없음) |

### 테이블 관계

| 테이블 | 컬럼 |
|---|---|
| `time` | `id BIGINT`, `time TIME` |
| `reservation` | `id BIGINT`, `name VARCHAR(255)`, `date DATE`, `time_id BIGINT` |

- 두 테이블의 `id`는 기본 키이며 `AUTO_INCREMENT`로 생성됩니다. 모든 컬럼은 `NOT NULL`입니다.
- `reservation.time_id`는 `time.id`를 참조하는 외래키입니다. 하나의 시간에 여러 예약이 연결될 수 있습니다.
- 예약에는 시간 ID를 저장하고, 조회할 때 `INNER JOIN`으로 실제 시간 정보를 함께 가져옵니다.
- 예약에서 참조 중인 시간은 외래키 제약으로 삭제할 수 없습니다. 먼저 해당 예약을 삭제해야 합니다.
- 사용 중인 시간 삭제 요청에는 `409 Conflict`와 삭제할 수 없는 이유를 응답하며, 기존 예약과 시간은 유지됩니다.
- 미션 예시의 `VARCHAR(255)` 대신 날짜에는 `DATE`, 시간에는 `TIME` 타입을 사용했습니다.

## 기능

### 화면

| 화면 | 경로 |
|---|---|
| 어드민 메인 | `GET /` |
| 예약 관리 | `GET /reservation` |
| 시간 관리 | `GET /time` |

- 예약 관리 화면은 `new-reservation.html`을 사용합니다.
- 예약 관리 페이지는 로드 시점에 예약 목록과 시간 목록을 조회합니다. 예약 시간은 등록된 시간표에서 선택합니다.
- 먼저 시간 관리 화면에서 시간을 등록한 뒤 예약을 추가합니다.

### 시간 관리

- 시간(`HH:mm`)을 등록하고 전체 시간 목록을 조회하거나 식별자로 삭제할 수 있습니다.
- 시간 식별자는 데이터베이스에서 자동으로 생성합니다.
- 시간이 누락되거나 형식이 올바르지 않으면 `400 Bad Request`를 응답합니다.
- 존재하지 않는 시간의 삭제 요청에는 `404 Not Found`를 응답합니다.

### 예약

하나의 예약은 다음 정보로 구성됩니다.

| 항목 | 설명 |
|---|---|
| `id` | 예약 식별자 |
| `name` | 예약자 이름 |
| `date` | 예약 날짜 (`yyyy-MM-dd`) |
| `time` | 시간 식별자와 실제 시간을 포함한 객체 (`id`, `time`) |

### 예약 조회

- 저장된 모든 예약을 JSON 배열로 응답합니다.
- 예약이 하나도 없으면 빈 배열을 응답합니다.
- 과거 날짜의 예약도 조회 대상에 포함됩니다.

### 예약 추가

- 예약자 이름, 날짜, 등록된 시간의 식별자를 받아 예약을 저장합니다.
- 요청 JSON의 필드명은 `time`이며, 값은 시간 문자열이 아닌 시간 ID입니다. Java 요청 DTO에서는 `@JsonProperty("time")`을 통해 `timeId`로 매핑합니다.
- 예약 자체의 식별자(`id`)는 요청에 포함하지 않으며, 데이터베이스가 자동으로 생성합니다.
- 생성에 성공하면 생성된 예약의 경로를 `Location` 헤더에, 예약 정보를 본문에 담아 응답합니다.
- 다음의 경우 예약을 생성하지 않습니다.
  - 예약자 이름이 비어있거나 공백만 있는 경우
  - 날짜가 비어있는 경우
  - 시간 ID가 비어있는 경우
  - 기존 방식처럼 `"time": "10:00"`을 전달하는 경우
  - 예약 일시가 현재 시각보다 과거인 경우 *(미션 요구사항 외 자체 추가)*

### 예약 취소

- 식별자로 예약을 삭제합니다.
- 존재하지 않거나 이미 삭제된 예약에도 삭제 목적을 달성한 것으로 보고 `204 No Content`를 반환합니다.
- 삭제 성공 시 응답 본문은 비어 있습니다.

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
        "time": {
            "id": 1,
            "time": "10:00"
        }
    }
]
```

### 예약 추가

아래 예시는 `10:00`인 시간이 ID `1`로 등록되어 있다고 가정합니다. 실제 요청 시에는 시간 등록 응답의 ID를 사용하고, 날짜는 선택한 시간과 합쳐 현재 시각 이후가 되도록 지정합니다.

**Request**

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "name": "브라운",
    "date": "2030-08-05",
    "time": 1
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
    "time": {
        "id": 1,
        "time": "10:00"
    }
}
```

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

### 예약 취소 (존재하지 않거나 이미 삭제된 예약)

같은 예약의 삭제를 재요청해도 `204 No Content`를 응답하며, 예약이 없는 상태를 유지합니다.

**Request**

```
DELETE /reservations/999 HTTP/1.1
```

**Response**

```
HTTP/1.1 204 No Content
```

### 예약 추가 실패 (과거 시간)

미션 요구사항에는 없지만, 현재 시각보다 과거의 날짜/시간으로는 예약할 수 없도록 직접 추가했습니다.

시간 ID `1`이 등록되어 있다는 전제의 예시입니다.

**Request**

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "name": "브라운",
    "date": "2020-01-01",
    "time": 1
}
```

**Response**

```
HTTP/1.1 400 Bad Request
```

### 시간 추가

**Request**

```http
POST /times HTTP/1.1
Content-Type: application/json

{
    "time": "10:00"
}
```

**Response**

```http
HTTP/1.1 201 Created
Location: /times/1
Content-Type: application/json

{
    "id": 1,
    "time": "10:00"
}
```

### 시간 목록 조회

**Request**

```http
GET /times HTTP/1.1
```

**Response**

```http
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "time": "10:00"
    }
]
```

등록된 시간이 없으면 빈 배열을 응답합니다.

### 시간 삭제

예약에서 참조하지 않는 시간을 삭제합니다.

**Request**

```http
DELETE /times/1 HTTP/1.1
```

**Response**

```http
HTTP/1.1 204 No Content
```

존재하지 않는 시간 ID로 요청하면 `404 Not Found`를 응답합니다.

### 시간 삭제 실패 (예약에서 사용 중인 시간)

시간 ID `1`을 사용하는 예약이 있는 상태에서 `DELETE /times/1`을 요청하면 다음과 같이 응답합니다.

```http
HTTP/1.1 409 Conflict
Content-Type: application/json

{
    "message": "예약에서 사용 중인 시간은 삭제할 수 없습니다."
}
```

기존 예약과 시간 및 연결 관계는 유지됩니다. 해당 시간을 사용하는 예약을 모두 삭제한 뒤 시간 삭제를 다시 요청할 수 있습니다.

## 프로젝트 구조

- Controller: HTTP 요청 처리와 응답 DTO 변환
- Service: 예약·시간 관리의 처리 흐름 조합
- Repository: 데이터베이스 저장·조회·삭제
- Domain: 예약과 시간의 상태 및 유효성 규칙

Controller → Service → Repository 순서로 호출하며,
Service에서 Domain 객체를 사용합니다.

## 단계별 테스트

- 8단계: 시간 추가·조회·삭제 API의 상태 코드와 생성 경로를 검증합니다.
- 9단계: 기존 시간 문자열을 사용하는 예약 요청이 `400 Bad Request`로 거절되는지 검증합니다.
- 10단계: `ReservationController`에 `JdbcTemplate` 필드가 없는지 검증합니다. Service 분리 전체를 검증하는 테스트는 아닙니다.
- 3·6·7단계의 기존 테스트는 주석으로 보관하고, 시간 ID 요청·외래키 저장·응답 DTO에 맞춘 테스트를 추가했습니다.
