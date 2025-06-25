# spring-roomescape-playground

## 1단계 - 홈 화면
### 요구사항
- [x] `localhost:8080` 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 `templates/home.html` 파일을 이용하세요.

### 요구사항 테스트
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {
    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }
}
```

## 2단계 - 예약 조회
### 요구사항
- [x] `/reservation` 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 `templates/reservation.html` 파일을 이용하세요.
- [x] 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

### 요구사항 테스트
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {
    
    //...
    
    @Test
    void 이단계() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservationResponses")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3)); // 아직 생성 요청이 없으니 Controller에서 임의로 넣어준 Reservation 갯수 만큼 검증하거나 0개임을 확인하세요.
    }
}
```
### API 명세
#### Request
```
GET /reservationResponses HTTP/1.1
```
#### Response
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
    }
]
```

## 3단계 - 예약 추가 / 취소
### 요구사항
- [x] API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- [x] 아래 화면에서 예약 추가와 취소가 잘 동작해야합니다.
### 요구사항 테스트
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StepTest {

    //...
   
    @Test
    void 삼단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservationResponses")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservationResponses/1")
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservationResponses")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservationResponses/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservationResponses")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}
```

### API 명세
#### 예약 추가 Request
```
POST /reservationResponses HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```
#### 예약 추가 Response
```
HTTP/1.1 201 
Location: /reservationResponses/1
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

#### 예약 취소 Request
```
DELETE /reservationResponses/1 HTTP/1.1
```
#### 예약 취소 Response
```
HTTP/1.1 204 No Content
```

## 4단계 - 예외 처리
### 요구사항
-[x] 예약 관련 API 호출 시 에러가 발생하는 경우 중 요청의 문제인 경우 Status Code를 400으로 응답하세요.
-[x] 예를 들면 `예약 추가 시 필요한 인자값이 비어있는 경우` 혹은 `삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없는 경우`가 있습니다.

### 요구사항 테스트
```java
@Test
void 사단계() {
    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "");
    params.put("time", "");

    // 필요한 인자가 없는 경우
    RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservationResponses")
            .then().log().all()
            .statusCode(400);

    // 삭제할 예약이 없는 경우
    RestAssured.given().log().all()
            .when().delete("/reservationResponses/1")
            .then().log().all()
            .statusCode(400);
}
```

## 5단계 - 데이터베이스 적용하기
### 요구사항
- [x] h2 데이터베이스를 활용하여 데이터를 저장하도록 수정하세요.

### 세부 요구사항
#### gradle 의존성 추가
- [x] build.gradle 파일을 이용하여 다음 두 의존성을 추가하세요.
  - [x] `spring-boot-stater-jdbc`
  - [x] `h2`
#### 테이블 스키마 정의
- [x] 데이터베이스 테이블 생성을 위해 `schema.sql` 파일을 생성하고 테이블을 생성하는 쿼리를 작성하세요.

#### 데이터베이스 설정
- [x] h2 데이터베이스의 console 기능을 활성화하세요.
- [x] datasource url을 다음과 같이 지정하세요.
    - [x] `jdbc:h2:mem:database`

### 요구사항 테스트
- [x] JdbcTemplate을 이용하여 DataSource객체에 접근하기
- [x] DataSource 객체를 이용하여 Connection 확인하기
- [x] Connection 객체를 이용하여 데이터베이스 이름 검증
- [x] Connection 객체를 이용하여 테이블 이름 검증

```java
@Autowired
private JdbcTemplate jdbcTemplate;

@Test
void 오단계() {
    try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
        assertThat(connection).isNotNull();
        assertThat(connection.getCatalog()).isEqualTo("DATABASE");
        assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
```

## 6단계 - 데이터 조회하기
### 요구사항
- [x] 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정하세요.

> 예약 추가/취소 API는 7단계에서 데이터베이스 전환이 진행되기 때문에 6단계에서는 예약 관리 기능이 정상동작 하지 않습니다.

### 요구사항 테스트
- [x] 데이터베이스에 예약 하나 추가 후 예약 조회 API를 통해 조회한 예약 수와 데이터베이스 쿼리를 통해 조회한 예약 수가 같은지 비교하는 테스트
```java
@Test
void 육단계() {
    jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

    List<Reservation> reservations = RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200).extract()
            .jsonPath().getList(".", Reservation.class);

    Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

    assertThat(reservations.size()).isEqualTo(count);
}
```

## 7단계 - 데이터 추가/삭제하기
### 요구사항
- [x] 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정하세요.
  - [x] 기존에 사용하던 List 및 AtomicLong 을 제거하세요.
- [x] 예약 관리 기능이 정상 동작하도록 기능을 완성하세요.

### 요구사항 테스트
- [x] 예약 추가 API를 활용하여 테이블에 예약 정보 추가
- [x] 조회 쿼리를 이용하여 데이터가 저장되었는지 확인
- [x] 예약 취소 API를 활용하여 테이블에 예약 정보 삭제
- [x] 조회 쿼리를 이용하여 데이터가 삭제되었는지 확인
```java
@Test
void 칠단계() {
    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2023-08-05");
    params.put("time", "10:00");

    RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(201)
            .header("Location", "/reservations/1");

    Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    assertThat(count).isEqualTo(1);

    RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(204);

    Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    assertThat(countAfterDelete).isEqualTo(0);
}

```

