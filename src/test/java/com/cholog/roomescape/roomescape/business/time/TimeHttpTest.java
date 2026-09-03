package com.cholog.roomescape.roomescape.business.time;

import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.dto.request.TimeRequest;
import com.cholog.roomescape.roomescape.dto.response.TimeResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TimeHttpTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("시간 생성에 성공")
    void createTime() {

        // given
        LocalTime time = LocalTime.of(10, 0).truncatedTo(ChronoUnit.MINUTES);
        TimeRequest request = new TimeRequest(time);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/times"));
    }

    @Test
    @DisplayName("시간이 null값이면 400을 반환한다.")
    void createTimeMustRequireTime() {

        // given
        LocalTime time = null;
        TimeRequest request = new TimeRequest(time);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("이미 존재하는 시각을 다시 생성하려 하면 409를 반환한다.")
    void createDuplicatedTime() {

        // given
        LocalTime time = LocalTime.of(10, 0).truncatedTo(ChronoUnit.MINUTES);
        TimeRequest request = new TimeRequest(time);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        // when & then
        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().get("/times")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    @DisplayName("시간 조회에 성공")
    void readTime() {

        // given
        LocalTime time = LocalTime.of(10, 0).truncatedTo(ChronoUnit.MINUTES);
        TimeRequest request = new TimeRequest(time);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", containsString("/times"));

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().get("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    @DisplayName("시간 삭제에 성공")
    void deleteTime() {

        // given
        LocalTime time = LocalTime.of(10, 0).truncatedTo(ChronoUnit.MINUTES);
        TimeRequest request = new TimeRequest(time);

        String location = RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("location");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().delete(location)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

    }

    @Test
    @DisplayName("저장된 적 없는 시각을 삭제하려 하면 404를 반환한다.")
    void deleteNotExistingTime() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().delete("/times/1")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("예약이 참조하고 있는 시각을 삭제하려 하면 409를 반환한다.")
    void deleteTimeReferencedByReservation() {

        // given
        LocalTime time = LocalTime.of(10, 0).truncatedTo(ChronoUnit.MINUTES);
        TimeRequest timeRequest = new TimeRequest(time);

        TimeResponse timeResponse = RestAssured
                .given()
                .body(timeRequest)
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().body().as(TimeResponse.class);

        ReservationRequest reservationRequest = new ReservationRequest(
                "Alice",
                LocalDate.of(2026, 8, 31),
                timeResponse.id().toString()
        );

        RestAssured
                .given()
                .body(reservationRequest)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        // when & then
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().delete("/times/" + timeResponse.id())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when().get("/times")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }
}
