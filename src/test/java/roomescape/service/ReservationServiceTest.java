package roomescape.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

class ReservationServiceTest {

    private static final Clock CLOCK = Clock.fixed(
            Instant.parse("2026-08-12T03:00:00Z"),
            ZoneId.of("Asia/Seoul")
    );

    private ReservationService reservationService;
    private ReservationRepository reservationRepository;
    private TimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        timeRepository = mock(TimeRepository.class);
        reservationService = new ReservationService(reservationRepository, timeRepository, CLOCK);
    }

    @Test
    @DisplayName("예약을 저장하고 조회한다")
    void savesAndFindsReservation() {
        LocalDate date = LocalDate.of(2026, 8, 13);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(15, 40));
        Reservation reservation = new Reservation(null, "브라운", date, reservationTime);
        Reservation savedReservation = reservation.withId(1L);

        when(timeRepository.findById(1L)).thenReturn(Optional.of(reservationTime));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);
        when(reservationRepository.findAll()).thenReturn(List.of(savedReservation));

        assertThat(reservationService.create("브라운", date, 1L).getId()).isEqualTo(1L);
        assertThat(reservationService.findAll()).containsExactly(savedReservation);
    }

    @Test
    @DisplayName("지난 일시로는 예약할 수 없다")
    void rejectsPastReservation() {
        LocalDate date = LocalDate.of(2026, 8, 12);
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(11, 59));
        when(timeRepository.findById(1L)).thenReturn(Optional.of(reservationTime));

        assertThatThrownBy(() -> reservationService.create("브라운", date, 1L))
                .isInstanceOf(InvalidReservationRequestException.class);
    }

    @Test
    @DisplayName("존재하지 않는 예약은 삭제할 수 없다")
    void rejectsDeletingNonexistentReservation() {
        assertThatThrownBy(() -> reservationService.deleteById(1L))
                .isInstanceOf(NotFoundReservationException.class)
                .hasMessageContaining("1");
    }
}
