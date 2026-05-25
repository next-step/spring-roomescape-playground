# Roomescape Reservation System

Spring Boot로 구현한 방탈출 예약 관리 애플리케이션입니다.  
관리자는 웹 화면에서 예약 목록을 조회하고, 예약을 추가하거나 삭제할 수 있습니다. 예약 데이터는 H2 인메모리 데이터베이스에 저장됩니다.

## 주요 기능

- 홈 화면 제공
- 예약 관리 화면 제공
- 예약 목록 조회
- 예약 생성
- 예약 삭제
- 예약 요청값 검증
- 과거 시간 예약 방지
- 동일 날짜 및 동일 시간 중복 예약 방지
- H2 Console 제공

## 프로젝트 구조

```text
src/main/java/roomescape
├── RoomescapeApplication.java
├── GlobalExceptionHandler.java
├── controller
│   ├── GreetingViewController.java
│   ├── ReservationController.java
│   └── ReservationViewController.java
├── domain
│   ├── Reservation.java
│   └── ReservationValidator.java
├── dto
│   ├── ReservationRequest.java
│   └── ReservationResponse.java
├── repository
│   └── ReservationRepository.java
└── service
    └── ReservationService.java
```

```text
src/main/resources
├── application.properties
├── schema.sql
├── static
│   ├── css
│   └── js
└── templates
    ├── home.html
    ├── reservation.html
    ├── new-reservation.html
    └── time.html
```

## 실행 방법

```bash
./gradlew bootRun
```

Windows PowerShell에서는 다음 명령을 사용할 수 있습니다.

```powershell
.\gradlew.bat bootRun
```

애플리케이션 실행 후 아래 주소로 접속합니다.

- 홈: `http://localhost:8080/`
- 예약 관리: `http://localhost:8080/reservation`
- H2 Console: `http://localhost:8080/h2-console`

H2 Console 접속 정보는 다음과 같습니다.

- JDBC URL: `jdbc:h2:mem:database`
- User Name: `sa`
- Password: 비워둠

## 테스트 실행

```bash
./gradlew test
```

Windows PowerShell에서는 다음 명령을 사용할 수 있습니다.

```powershell
.\gradlew.bat test
```

## API

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
    "date": "2099-08-05",
    "time": "11:40"
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
  "date": "2099-08-05",
  "time": "11:40"
}
```

성공 시 `201 Created`를 반환하고, `Location` 헤더에 생성된 예약 경로를 포함합니다.

```http
Location: /reservations/1
```

### 예약 삭제

```http
DELETE /reservations/{id}
```

성공 시 `204 No Content`를 반환합니다.

## 검증 정책

예약 생성 요청은 다음 조건을 만족해야 합니다.

- `name`은 빈 값일 수 없습니다.
- `date`는 필수 값이며 `yyyy-MM-dd` 형식으로 전달합니다.
- `time`은 빈 값일 수 없고 `HH:mm` 형식이어야 합니다.
- 시간은 `00:00`부터 `23:59`까지만 허용합니다.
- 현재 서버 시간보다 이전인 예약은 생성할 수 없습니다.
- 같은 날짜와 같은 시간의 예약은 중복 생성할 수 없습니다.

검증 실패, 잘못된 요청 본문, 존재하지 않는 예약 삭제 요청은 `400 Bad Request`를 반환합니다.

## 데이터베이스

애플리케이션은 H2 인메모리 데이터베이스를 사용합니다. 시작 시 `schema.sql`을 통해 `reservation` 테이블이 생성됩니다.

```sql
CREATE TABLE reservation
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```

## 참고

현재 백엔드에서 구현된 API는 예약 관리(`/reservations`)입니다. 정적 리소스와 템플릿에는 시간 관리 화면 및 관련 JavaScript도 포함되어 있지만, `/times` API는 아직 구현되어 있지 않습니다.
