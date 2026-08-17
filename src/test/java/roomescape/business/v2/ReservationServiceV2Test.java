package roomescape.business.v2;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.dto.request.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepositoryV1;
import roomescape.repository.ReservationRepositoryV2;
import roomescape.service.ReservationService;
import roomescape.service.ReservationServiceV1;
import roomescape.service.ReservationServiceV2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
public class ReservationServiceV2Test {

    private ReservationService reservationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        this.reservationService = new ReservationServiceV2(new ReservationRepositoryV2(jdbcTemplate));
    }

    @AfterEach
    public void teardown() {
        this.reservationService = null;
    }

    @Test
    @DisplayName("createReservation을 호출하면, 예약 엔터티를 반환한다")
    void createReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), LocalTime.now());

        // when
        Reservation createdReservation = reservationService.createReservation(request);

        // then
        assertThat(createdReservation.getId()).isNotNull();
        assertThat(createdReservation.getName()).isEqualTo(request.name());
        assertThat(createdReservation.getDate()).isEqualTo(request.date());
        assertThat(createdReservation.getTime()).isEqualTo(request.time());
    }

    @Test
    @DisplayName("findAllReservations를 호출하면, 저장되어 있는 모든 예약을 반환한다.")
    void findAllReservationsTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), LocalTime.now());
        Reservation isNotSaved = new Reservation("Bob", LocalDate.now(), LocalTime.now());
        Reservation createdReservation = reservationService.createReservation(request);

        // when
        List<Reservation> reservations = reservationService.findAllReservations();

        // then
        assertThat(reservations.contains(createdReservation)).isTrue();
        assertThat(reservations.contains(isNotSaved)).isFalse();
    }

    @Test
    @DisplayName("deleteReservation()을 호출하면 저장되어 있는 레코드를 삭제한다.")
    void deleteReservationTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), LocalTime.now());
        Reservation createdReservation = reservationService.createReservation(request);

        // when
        reservationService.deleteReservation(createdReservation.getId());

        // then
        assertThat(reservationService.findAllReservations().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("잘못된 ID로 deleteReservation()을 호출하면 예외가 발생한다.")
    void deleteReservationByIllegalIdTest() {
        // given
        ReservationRequest request = new ReservationRequest("Alice", LocalDate.now(), LocalTime.now());
        Reservation createdReservation = reservationService.createReservation(request);

        // then
        Assertions.assertThrows(
                ReservationNotFoundException.class,

                // when
                () -> reservationService
                        .deleteReservation(
                                createdReservation.getId() + 1L
                        )
        );
        assertThat(reservationService.findAllReservations().size()).isEqualTo(1);
    }
}
