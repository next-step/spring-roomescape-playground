package roomescape.dto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.RoomEscapeApplication;
import roomescape.dto.request.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,  classes = RoomEscapeApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationRequestTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("이름 필드는 비어있을 수 없다")
    void nameMustRequiredInCreatedReservationRequest() {

        // given
        String name = "";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                // then
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("이름 필드는 최대 20자를 초과할 수 없다")
    void nameCannotExceed20CharInCreatedReservationRequest() {

        // given
        String name = "abcdefghijklmnopqrstu";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                // then
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("이름 필드는 null값을 허용하지 않는다")
    void nameMustNotBeNullInCreatedReservationRequest() {

        // given
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                null, reservedDate, reservedTime
        );

        // when
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                // then
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약 날짜 필드는 null값을 허용하지 않는다")
    void dateMustNotBeNullInCreatedReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = null;
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                // then
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약 시각 필드는 null값을 허용하지 않는다")
    void timeMustNotBeNullInCreatedReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = null;

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                // then
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약을 정상적으로 처리한 경우")
    void allValidatedInCreatedReservationRequest() {

        // given
        String name = "Alice";
        LocalDate reservedDate = LocalDate.now();
        LocalTime reservedTime = LocalTime.now();

        ReservationRequest request = new ReservationRequest(
                name, reservedDate, reservedTime
        );

        // when
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                // then
                .statusCode(HttpStatus.FOUND.value());
    }

}
