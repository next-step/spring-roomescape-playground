package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.repository.ReservationRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationServiceTest {

    private static final String NAME = "브라운";
    private static final LocalDate TODAY = LocalDate.of(2023, 1, 2);
    private static final LocalTime NOW = LocalTime.of(10, 30);

    private static final Long NON_EXISTENT_ID = 999L;

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
    void 현재_시각과_동일한_시각의_예약은_생성할_수_없다() {
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
                DuplicateReservationException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        reservationTime
                )
        );
    }

    @Test
    void 존재하는_예약을_삭제할_수_있다() {
        LocalTime reservationTime = NOW.plusHours(2);

        Reservation savedReservation = reservationService.create(NAME, TODAY, reservationTime);
        Long id = savedReservation.getId();

        reservationService.delete(id);

        boolean exists = reservationRepository.findAll().stream()
                .anyMatch(reservation -> reservation.getId().equals(id));

        assertFalse(exists);
    }

    @Test
    void 존재하지_않는_예약을_삭제하면_예외가_발생한다() {
        assertThrows(
                ReservationNotFoundException.class,
                () -> reservationService.delete(NON_EXISTENT_ID)
        );
    }
}
