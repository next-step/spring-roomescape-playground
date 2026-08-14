package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.InMemoryReservationRepository;

class ReservationServiceTest {

    private static final Clock CLOCK = Clock.fixed(
            Instant.parse("2026-08-12T03:00:00Z"),
            ZoneId.of("Asia/Seoul")
    );

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(new InMemoryReservationRepository(), CLOCK);
    }

    @Test
    @DisplayName("예약을 저장하고 조회한다")
    void savesAndFindsReservation() {
        Reservation reservation = new Reservation(
                null,
                "브라운",
                LocalDate.of(2026, 8, 13),
                LocalTime.of(15, 40)
        );

        Reservation savedReservation = reservationService.create(reservation);

        assertThat(savedReservation.getId()).isEqualTo(1L);
        assertThat(reservationService.findAll()).containsExactly(savedReservation);
    }

    @Test
    @DisplayName("지난 일시로는 예약할 수 없다")
    void rejectsPastReservation() {
        Reservation reservation = new Reservation(
                null,
                "브라운",
                LocalDate.of(2026, 8, 12),
                LocalTime.of(11, 59)
        );

        assertThatThrownBy(() -> reservationService.create(reservation))
                .isInstanceOf(InvalidReservationRequestException.class)
                .hasMessage("지난 일시로는 예약할 수 없습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 예약은 삭제할 수 없다")
    void rejectsDeletingNonexistentReservation() {
        assertThatThrownBy(() -> reservationService.deleteById(1L))
                .isInstanceOf(NotFoundReservationException.class)
                .hasMessageContaining("1");
    }
}
