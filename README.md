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

1. 도메인 주도 검증 및 타입 개선
    - Reservation 객체 내부로 데이터 검증 책임을 이동하여 객체의 응집도를 높이고 컨트롤러를 경량화했습니다.
    - 날짜와 시간을 String 대신 자바 8의 LocalDate, LocalTime API로 변경하여 검증(과거 날짜 예약 방지 등) 및 비교 연산의 안전성을 확보했습니다.

2. 방탈출 전용 계층형 커스텀 예외 도입
    - 최상위 부모 예외인 RoomescapeException을 정의하여 프로젝트의 비즈니스 예외를 그룹화했습니다.
    - InvalidReservationException (필수값 누락, 과거 날짜 등)
    - DuplicateReservationException (중복 예약)
    - NotFoundReservationException (존재하지 않는 예약 조회/삭제)

3. GlobalExceptionHandler를 통한 전역 예외 처리 및 로깅
    - 단순 콘솔 출력 대신 SLF4J 로거를 도입하여 목적에 맞게 로그 레벨(INFO, WARN, ERROR)을 세분화했습니다.
    - 예외 발생 시 내부 에러 로그 노출 없이, RESTful 설계에 맞는 상태 코드(400 Bad Request, 404 Not Found, 409 Conflict)와 명확한 에러 메시지를 응답 본문(Body)에 포함하여 클라이언트의 대처를 용이하게 했습니다.
    - 데이터 타입 오류(MethodArgumentTypeMismatchException) 및 JSON 파싱 오류(HttpMessageNotReadableException) 등 스프링 내장 예외 방어 로직을 추가했습니다.

## 5단계 요구사항
- 기존 애플리케이션 메모리(List) 기반으로 관리되던 예약 데이터를 관계형 데이터베이스인 H2 Database로 이전합니다.
- schema.sql 파일을 작성하여 애플리케이션 구동 시 예약 테이블(reservation)이 자동으로 생성되도록 스키마를 구성합니다.

## 6단계 요구사항
- 순수 JDBC API 사용 시 발생하는 반복적인 코드를 제거하기 위해 스프링이 제공하는 JdbcTemplate을 도입합니다.

## 7단계 요구사항
- 데이터베이스 접근 로직을 전담하는 ReservationDao 객체를 만들어 책임을 분리합니다.
- 기존에 구현해 두었던 예약 조회, 추가, 삭제 API가 DB와 정상적으로 연동되도록 리팩터링합니다.

## 8단계 요구사항
- 예약 시간을 직접 입력하는 방식에서, 등록된 시간표에서 선택하는 방식으로 변경하기 위해 시간(Time) 도메인을 추가합니다.
- Time 도메인에 대한 CRUD API(/times)를 구현하고, H2 데이터베이스(time 테이블)와 연동합니다.

## 9단계 요구사항
- 뷰 템플릿 변경: templates/new-reservation.html 파일을 활용하도록 변경합니다.
- 테이블 관계 설정(FK): reservation 테이블이 time 테이블의 id를 참조하도록 외래키(time_id)를 지정합니다.
- 도메인 모델 개선: Reservation 클래스가 시간 문자열이나 ID가 아닌 Time 객체 자체를 필드로 가지도록 수정합니다.
- DTO 분리: 클라이언트 요청(timeId 전달)과 도메인(Time 객체) 사이의 불일치를 해결하기 위해 Request/Response DTO 패턴을 적용합니다.
- INNER JOIN 적용: 예약 목록 조회 시 reservation과 time 테이블을 조인하여 연관된 데이터를 한 번에 가져오도록 쿼리를 수정합니다.

## 10단계 요구사항
- 애플리케이션을 역할과 책임에 따라 3계층(Controller - Service - Dao)으로 분리합니다.
- Controller: 웹 요청/응답 처리 및 DTO 변환 책임
- Service: 비즈니스 로직(중복 검증 등) 흐름 제어 및 트랜잭션 책임
- Dao: JdbcTemplate을 활용한 순수 데이터베이스 접근 책임
- 모든 의존성 주입은 스프링 컨테이너가 관리하는 생성자 주입 방식을 채택합니다.

---

# 데이터베이스 스키마

~~~sql
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS time;

CREATE TABLE time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES time(id)
);
~~~

---

# API 명세

## 시간(Time) API

#### 시간 조회 Request

~~~http
GET /times HTTP/1.1
~~~

#### 시간 조회 Response

~~~http
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "time": "10:00"
    },
    {
        "id": 2,
        "time": "13:00"
    }
]
~~~

#### 시간 추가 Request

~~~http
POST /times HTTP/1.1
Content-Type: application/json

{
    "time": "10:00"
}
~~~

#### 시간 추가 Response

~~~http
HTTP/1.1 201 Created
Location: /times/1
Content-Type: application/json

{
    "id": 1,
    "time": "10:00"
}
~~~

#### 시간 삭제 Request

~~~http
DELETE /times/1 HTTP/1.1
~~~

#### 시간 삭제 Response

~~~http
HTTP/1.1 204 No Content
~~~

## 예약(Reservation) API

#### 예약 조회 Request

~~~http
GET /reservations HTTP/1.1
~~~

#### 예약 조회 Response

~~~http
HTTP/1.1 200 OK
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
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": {
            "id": 2,
            "time": "11:00"
        }
    }
]
~~~

#### 예약 추가 Request

~~~http
POST /reservations HTTP/1.1
Content-Type: application/json

{
    "name": "브라운",
    "date": "2023-08-05",
    "timeId": 1
}
~~~

#### 예약 추가 Response

~~~http
HTTP/1.1 201 Created
Location: /reservations/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": {
        "id": 1,
        "time": "10:00"
    }
}
~~~

#### 예약 취소 Request

~~~http
DELETE /reservations/1 HTTP/1.1
~~~

#### 예약 취소 Response

~~~http
HTTP/1.1 204 No Content
~~~
