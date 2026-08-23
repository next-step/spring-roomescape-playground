package com.cholog.roomescape.roomescape.business;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationHttpTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = this.port;
    }

    @Test
    @DisplayName("예약 생성에 성공")
    void createReservation() {

        // given
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        ReservationRequest request = new ReservationRequest("Alice", date, time);

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
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        ReservationRequest request = new ReservationRequest(name, date, time);

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
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        ReservationRequest request = new ReservationRequest(name, date, time);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("날짜가 빈 값이면 400을 반환한다.")
    void createReservationMustRequiredDate() {

        // given
        String name = "Alice";
        LocalDate date = null;
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        ReservationRequest request = new ReservationRequest(name, date, time);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("날짜가 빈 값이면 400을 반환한다.")
    void createReservationMustRequiredTime() {

        // given
        String name = "Alice";
        LocalDate date = null;
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        ReservationRequest request = new ReservationRequest(name, date, time);

        RestAssured
                .given()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약 조회에 성공")
    void readReservation() {

        // given
        createReservation();

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }
}
