package com.cholog.roomescape.roomescape.business.reservation;

import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.dto.request.TimeRequest;
import com.cholog.roomescape.roomescape.dto.response.TimeResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationHttpTest {

    private static LocalTime dummyTime = LocalTime.of(10, 0);
    private TimeResponse timeResponse;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;

        TimeRequest request = new TimeRequest(dummyTime);

        timeResponse = RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then().extract().body().as(TimeResponse.class);
    }

    private String savedTimeId() {
        return timeResponse.id().toString();
    }

    @Test
    @DisplayName("예약 생성에 성공")
    void createReservation() {

        // given
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest("Alice", date, savedTimeId());

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/reservations"));
    }

    @Test
    @DisplayName("이름이 null 값이면 400을 반환한다.")
    void createReservationMustRequiredName() {

        // given
        String name = null;
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest(name, date, savedTimeId());

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("이름이 빈 문자열이면 400을 반환한다.")
    void createReservationRequiredNotBlankName() {

        // given
        String name = "";
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest(name, date, savedTimeId());

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("날짜가 null 값이면 400을 반환한다.")
    void createReservationMustRequiredDate() {

        // given
        String name = "Alice";
        LocalDate date = null;
        ReservationRequest request = new ReservationRequest(name, date, savedTimeId());

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("시간이 null 값이면 400을 반환한다.")
    void createReservationMustRequiredTime() {

        // given
        String name = "Alice";
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest(name, date, null);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("저장된 적 없는 시각 id로 예약을 요청하면 400을 반환한다.")
    void createReservationWithNotExistingTime() {

        // given
        String notExistingTimeId = String.valueOf(timeResponse.id() + 1L);
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest("Alice", date, notExistingTimeId);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("시각 id가 숫자가 아니면 400을 반환한다.")
    void createReservationWithNotNumericTime() {

        // given
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest("Alice", date, "10:00");

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("동일한 이름, 날짜, 시각으로 중복 예약을 요청하면 409를 반환한다.")
    void createDuplicatedReservation() {

        // given
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest("Alice", date, savedTimeId());

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        // when & then
        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().get("/reservations")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약 조회에 성공")
    void readReservation() {

        // given
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest("Alice", date, savedTimeId());

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/reservations"));

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약 삭제에 성공")
    void deleteTime() {

        // given
        LocalDate date = LocalDate.of(2026, 8, 31);
        ReservationRequest request = new ReservationRequest("Alice", date, savedTimeId());

        String location = RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().delete(location)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    @DisplayName("저장된 적 없는 예약을 삭제하려 하면 404를 반환한다.")
    void deleteNotExistingReservation() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().delete("/reservations/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
