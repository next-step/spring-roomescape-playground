# Roomescape Reservation System

Spring Boot로 구현한 방탈출 예약 관리 애플리케이션입니다. 예약 가능한 시간을 관리하고, 사용자는 등록된 시간 중 하나를 선택해 예약을 생성할 수 있습니다. 웹 화면은 Thymeleaf 템플릿으로 제공하며, 데이터는 H2 인메모리 데이터베이스에 저장됩니다.


## 주요 기능

- 홈 화면 제공
- 예약 관리 화면 제공
- 예약 시간 관리 화면 제공
- 예약 시간 목록 조회, 생성, 삭제
- 예약 목록 조회, 생성, 삭제
- 예약 요청값 검증
- 존재하지 않는 예약 시간으로 예약 생성 방지
- 과거 날짜/시간 예약 방지
- 같은 날짜와 같은 시간의 중복 예약 방지
- H2 Console 제공

### 예약 시간 생성

```http
POST /times
Content-Type: application/json
```

요청 예시:

```json
{
  "time": "20:00"
}
```

성공 시 `201 Created`를 반환하고, `Location` 헤더는 `/times/{id}` 형식입니다.

### 예약 시간 삭제

```http
DELETE /times/{id}
```

성공 시 `204 No Content`를 반환합니다.

### 예약 목록 조회

```http
GET /reservations
```

응답 예시:

```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2999-08-05",
    "time": {
      "id": 1,
      "time": "10:00"
    }
  }
]
```

### 예약 생성

```http
POST /reservations
Content-Type: application/json
```

요청 예시:

```json
{
  "name": "브라운",
  "date": "2999-08-05",
  "time": 1
}
```

`time`에는 `/times`로 조회하거나 생성한 예약 시간의 `id`를 전달합니다. 성공 시 `201 Created`를 반환하고, `Location` 헤더는 `/reservations/{id}` 형식입니다.

### 예약 삭제

```http
DELETE /reservations/{id}
```

성공 시 `204 No Content`를 반환합니다.

## 검증 정책

예약 시간 생성 요청은 다음 조건을 만족해야 합니다.

- `time`은 빈 값일 수 없습니다.
- `time`은 `HH:mm` 형식이어야 합니다.
- 허용 범위는 `00:00`부터 `23:59`까지입니다.

예약 생성 요청은 다음 조건을 만족해야 합니다.

- `name`은 빈 값일 수 없습니다.
- `date`는 필수 값이며 `yyyy-MM-dd` 형식이어야 합니다.
- `time`은 필수 값이며 존재하는 예약 시간 ID여야 합니다.
- 현재 서버 시간보다 이전인 예약은 생성할 수 없습니다.
- 같은 날짜와 같은 시간의 예약은 중복 생성할 수 없습니다.

검증 실패, 잘못된 요청 본문, 존재하지 않는 예약/시간 삭제 요청은 `400 Bad Request`를 반환합니다.

## 데이터베이스

애플리케이션은 H2 인메모리 데이터베이스를 사용합니다. 시작 시 `schema.sql`로 테이블을 생성하고, `data.sql`로 기본 예약 시간을 등록합니다.

```sql
CREATE TABLE time
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id)
);
```

기본 예약 시간:

```sql
INSERT INTO time (time) VALUES ('10:00');
INSERT INTO time (time) VALUES ('13:00');
INSERT INTO time (time) VALUES ('17:00');
```

## 프로젝트 구조

```text
src/main/java/roomescape
├── RoomescapeApplication.java
├── GlobalExceptionHandler.java
├── controller
│   ├── GreetingViewController.java
│   ├── ReservationController.java
│   ├── ReservationViewController.java
│   ├── TimeController.java
│   └── TimeViewController.java
├── domain
│   ├── Reservation.java
│   ├── ReservationValidator.java
│   └── Time.java
├── dto
│   ├── ReservationRequest.java
│   ├── ReservationResponse.java
│   ├── TimeRequest.java
│   └── TimeResponse.java
├── repository
│   ├── ReservationDao.java
│   ├── ReservationRepository.java
│   ├── TimeDao.java
│   └── TimeRepository.java
└── service
    ├── ReservationService.java
    └── TimeService.java
```

```text
src/main/resources
├── application.properties
├── data.sql
├── schema.sql
├── static
│   ├── css
│   └── js
└── templates
    ├── home.html
    ├── new-reservation.html
    ├── reservation.html
    └── time.html
```
