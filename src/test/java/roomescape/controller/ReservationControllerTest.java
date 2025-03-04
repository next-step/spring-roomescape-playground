package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2023-08-01T00:00:00Z"), ZoneId.of("UTC"));

    @SpyBean
    private Clock clock;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void clearDatabase() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");

        jdbcTemplate.update("DELETE FROM time");
        jdbcTemplate.update("ALTER TABLE time ALTER COLUMN id RESTART WITH 1");
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    void setupRestAssured() {
        RestAssured.port = port;
    }

    @BeforeEach
    void setupClock() {
        doReturn(FIXED_CLOCK.instant()).when(clock).instant();
        doReturn(FIXED_CLOCK.getZone()).when(clock).getZone();
    }

    @Test
    void 예약을_생성할_수_있다() {
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);
        final CreateReservationRequest request = new CreateReservationRequest("김철수", LocalDate.of(2024, 3, 3), savedTime.getId());

        final ReservationResponse response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/reservations/1")
                .extract()
                .as(ReservationResponse.class);

        final List<Reservation> reservations = reservationRepository.findAll();

        assertAll(
                () -> assertThat(response.name()).isEqualTo(request.name()),
                () -> assertThat(response.date()).isEqualTo(request.date()),
                () -> assertThat(response.time().id()).isEqualTo(request.timeId()),
                () -> assertThat(reservations).hasSize(1)
        );
    }

    @Test
    void 시간이_현재보다_과거이면_예약_생성에_실패한다() {
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);
        final CreateReservationRequest request = new CreateReservationRequest("김철수", LocalDate.of(2023, 7, 31), savedTime.getId());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 해당_시간에_예약이_존재하면_예약_생성에_실패한다() {
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);

        final CreateReservationRequest request = new CreateReservationRequest("김철수", LocalDate.of(2025, 3, 3), savedTime.getId());
        reservationRepository.save(request.toReservation(savedTime));

        final CreateReservationRequest targetRequest = new CreateReservationRequest("김영희", LocalDate.of(2025, 3, 3), savedTime.getId());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(targetRequest)
                .when().post("/reservations")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 생성된_모든_예약을_조회할_수_있다() {
        final Time time1 = new Time(LocalTime.of(13, 0));
        final Time savedTime1 = timeRepository.save(time1);
        final Time time2 = new Time(LocalTime.of(14, 0));
        final Time savedTime2 = timeRepository.save(time2);

        final CreateReservationRequest request1 = new CreateReservationRequest("김철수", LocalDate.of(2025, 3, 3), savedTime1.getId());
        reservationRepository.save(request1.toReservation(savedTime1));
        final CreateReservationRequest request2 = new CreateReservationRequest("김영희", LocalDate.of(2025, 3, 3), savedTime2.getId());
        reservationRepository.save(request2.toReservation(savedTime2));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", is(2));
    }

    @Test
    void 특정_예약을_삭제할_수_있다() {
        final Time time = new Time(LocalTime.of(13, 0));
        final Time savedTime = timeRepository.save(time);
        final CreateReservationRequest request = new CreateReservationRequest("김철수", LocalDate.of(2024, 3, 3), savedTime.getId());
        reservationRepository.save(request.toReservation(savedTime));

        RestAssured.given()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 해당예약이_존재하지_않으면_예약_삭제에_실패한다() {
        RestAssured.given()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
