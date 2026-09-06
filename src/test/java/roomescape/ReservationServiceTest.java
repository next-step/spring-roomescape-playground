package roomescape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
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
        reservationService =
                new ReservationService(reservationRepository, fixedClock);
    }

    @Test
    void 과거_시각의_예약은_거부한다() {
        ReservationRequest request =
                new ReservationRequest("브라운", "2026-09-06", "09:59");

        assertThatThrownBy(() -> reservationService.create(request))
                .isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void 현재와_같은_시각의_예약은_거부한다() {
        ReservationRequest request =
                new ReservationRequest("브라운", "2026-09-06", "10:00");

        assertThatThrownBy(() -> reservationService.create(request))
                .isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(reservationRepository);
    }

    @Test
    void 미래_시각의_예약은_저장한다() {
        ReservationRequest request =
                new ReservationRequest("브라운", "2026-09-06", "10:01");

        reservationService.create(request);

        verify(reservationRepository).save(request);
    }

    @Test
    void 과거_시각으로_예약을_수정할_수_없다() {
        ReservationRequest request =
                new ReservationRequest("브라운", "2026-09-06", "09:59");

        assertThatThrownBy(() -> reservationService.update(1L, request))
                .isInstanceOf(InvalidReservationException.class);

        verifyNoInteractions(reservationRepository);
    }
}
