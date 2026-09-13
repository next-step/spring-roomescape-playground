package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import java.time.LocalDate;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private TimeRepository timeRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        ZoneId zone = ZoneId.of("Asia/Seoul");

        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2026, 9, 6, 10, 0)
                        .atZone(zone)
                        .toInstant(),
                zone
        );

        reservationRepository = mock(ReservationRepository.class);
        timeRepository = mock(TimeRepository.class);

        reservationService = new ReservationService(
                reservationRepository,
                timeRepository,
                fixedClock
        );
    }

    private void 시간등록(String time) {
        when(timeRepository.findById(1L))
                .thenReturn(Optional.of(
                        new ReservationTime(1L, LocalTime.parse(time))
                ));
    }

    @Test
    void 과거_시각의_예약은_거부한다() {
        시간등록("09:59");

        assertThatThrownBy(() -> reservationService.create(
                "브라운", "2026-09-06", 1L
        )).isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void 현재와_같은_시각의_예약은_거부한다() {
        시간등록("10:00");

        assertThatThrownBy(() -> reservationService.create(
                "브라운", "2026-09-06", 1L
        )).isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void 미래_시각의_예약은_저장한다() {
        시간등록("10:01");

        reservationService.create("브라운", "2026-09-06", 1L);

        ArgumentCaptor<Reservation> captor =
                ArgumentCaptor.forClass(Reservation.class);

        verify(reservationRepository).save(captor.capture());

        Reservation saved = captor.getValue();

        assertThat(saved.getName()).isEqualTo("브라운");
        assertThat(saved.getDate()).isEqualTo(LocalDate.of(2026, 9, 6));
        assertThat(saved.getTime().getId()).isEqualTo(1L);
        assertThat(saved.getTime().getTime())
                .isEqualTo(LocalTime.of(10, 1));
    }

    @Test
    void 과거_시각으로_예약을_수정할_수_없다() {
        시간등록("09:59");

        assertThatThrownBy(() -> reservationService.update(
                1L, "브라운", "2026-09-06", 1L
        )).isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void 미래_시각으로_예약을_수정한다() {
        시간등록("11:00");
        when(reservationRepository.update(any(Reservation.class)))
                .thenReturn(1);

        Reservation updated = reservationService.update(
                1L, "브라운", "2026-09-06", 1L
        );

        ArgumentCaptor<Reservation> captor =
                ArgumentCaptor.forClass(Reservation.class);

        verify(reservationRepository).update(captor.capture());

        assertThat(captor.getValue().getId()).isEqualTo(1L);
        assertThat(captor.getValue().getTime().getTime())
                .isEqualTo(LocalTime.of(11, 0));

        assertThat(updated.getName()).isEqualTo("브라운");
        assertThat(updated.getDate()).isEqualTo(LocalDate.of(2026, 9, 6));
    }

    @Test
    void 존재하지_않는_날짜로_예약할_수_없다() {
        시간등록("10:00");

        assertThatThrownBy(() -> reservationService.create(
                "브라운", "2026-99-99", 1L
        )).isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void 이름이_비어_있으면_예약할_수_없다() {
        assertThatThrownBy(() -> reservationService.create(
                " ", "2026-09-07", 1L
        )).isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(timeRepository, reservationRepository);
    }
}