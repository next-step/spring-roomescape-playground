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

## 기능

### 화면

| 화면 | 경로 |
|---|---|
| 어드민 메인 | `GET /` |
| 예약 관리 | `GET /reservation` |

- 예약 관리 페이지는 로드 시점에 예약 목록 조회 API를 호출하여 목록을 채웁니다.

### 예약

하나의 예약은 다음 정보로 구성됩니다.

| 항목 | 설명 |
|---|---|
| `id` | 예약 식별자 |
| `name` | 예약자 이름 |
| `date` | 예약 날짜 (`yyyy-MM-dd`) |
| `time` | 예약 시간 (`HH:mm`) |

### 예약 조회

- 저장된 모든 예약을 JSON 배열로 응답합니다.
- 예약이 하나도 없으면 빈 배열을 응답합니다.
- 과거 날짜의 예약도 조회 대상에 포함됩니다.

### 예약 추가

- 예약자 이름, 날짜, 시간을 받아 예약을 저장합니다.
- 식별자는 요청에 포함하지 않으며, 데이터베이스가 자동으로 생성합니다.
- 생성에 성공하면 생성된 예약의 경로를 `Location` 헤더에, 예약 정보를 본문에 담아 응답합니다.
- 다음의 경우 예약을 생성하지 않습니다.
  - 예약자 이름이 비어있거나 공백만 있는 경우
  - 날짜가 비어있는 경우
  - 시간이 비어있는 경우
  - 예약 일시가 현재 시각보다 과거인 경우 *(미션 요구사항 외 자체 추가)*

### 예약 취소

- 식별자로 예약을 삭제합니다.
- 존재하지 않는 식별자로 요청하면 예약을 찾을 수 없다는 응답을 반환합니다.
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
