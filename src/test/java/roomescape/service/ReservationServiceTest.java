package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.InMemoryReservationRepository;

class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(new InMemoryReservationRepository());
    }

    @Test
    @DisplayName("예약을 저장하고 조회한다")
    void savesAndFindsReservation() {
        Reservation reservation = new Reservation(
                null,
                "브라운",
                LocalDate.of(2026, 8, 11),
                LocalTime.of(15, 40)
        );

        Reservation savedReservation = reservationService.create(reservation);

        assertThat(savedReservation.getId()).isEqualTo(1L);
        assertThat(reservationService.findAll()).containsExactly(savedReservation);
    }

    @Test
    @DisplayName("존재하지 않는 예약은 삭제할 수 없다")
    void rejectsDeletingNonexistentReservation() {
        assertThatThrownBy(() -> reservationService.deleteById(1L))
                .isInstanceOf(NotFoundReservationException.class)
                .hasMessageContaining("1");
    }
}
