package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.repository.ReservationRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationServiceTest {

    private static final String NAME = "브라운";
    private static final LocalDate TODAY = LocalDate.of(2023, 1, 2);
    private static final LocalTime NOW = LocalTime.of(10, 30);

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private static final Clock FIXED_CLOCK = Clock.fixed(
            LocalDateTime.of(TODAY, NOW).atZone(ZONE_ID).toInstant(),
            ZONE_ID
    );

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository();
        reservationService = new ReservationService(reservationRepository, FIXED_CLOCK);
    }

    @Test
    void 과거_날짜로_예약하면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY.minusDays(1),
                        NOW
                )
        );
    }

    @Test
    void 오늘_현재보다_이전_시간으로_예약하면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        NOW.minusMinutes(1)
                )
        );
    }

    @Test
    void 현재와_같은_분으로_예약하면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        NOW
                )
        );
    }

    @Test
    void 현재보다_이후_시간으로_예약할_수_있다() {
        assertDoesNotThrow(
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        NOW.plusMinutes(1)
                )
        );
    }

    @Test
    void 중복_예약을_생성하면_예외가_발생한다() {
        LocalTime reservationTime = NOW.plusHours(2);

        reservationService.create(NAME, TODAY, reservationTime);

        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        reservationTime
                )
        );
    }
}
