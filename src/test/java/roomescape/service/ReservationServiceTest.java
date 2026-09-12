package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.TimeNotFoundException;
import roomescape.repository.ReservationRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.ArgumentCaptor;
import roomescape.repository.TimeRepository;

public class ReservationServiceTest {

    private static final String NAME = "브라운";
    private static final LocalDate TODAY = LocalDate.of(2023, 1, 2);
    private static final LocalTime NOW = LocalTime.of(10, 0);

    private static final Long NON_EXISTENT_ID = 999L;

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private static final Clock FIXED_CLOCK = Clock.fixed(
            LocalDateTime.of(TODAY, NOW).atZone(ZONE_ID).toInstant(),
            ZONE_ID
    );

    private ReservationRepository reservationRepository;
    private TimeRepository timeRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        timeRepository = mock(TimeRepository.class);

        reservationService = new ReservationService(reservationRepository, timeRepository, FIXED_CLOCK);
    }

    @Test
    void 과거_날짜로_예약하면_예외가_발생한다() {
        Time time = new Time(1L, NOW);

        when(timeRepository.findById(time.getId()))
                .thenReturn(Optional.of(time));

        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY.minusDays(1),
                        time.getId()
                )
        );
    }

    @Test
    void 오늘_현재보다_이전_시간으로_예약하면_예외가_발생한다() {
        Time time = new Time(1L, NOW.minusMinutes(1));

        when(timeRepository.findById(time.getId()))
                .thenReturn(Optional.of(time));

        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        time.getId()
                )
        );
    }

    @Test
    void 현재_시각과_동일한_시각의_예약은_생성할_수_없다() {
        Time time = new Time(1L, NOW);

        when(timeRepository.findById(time.getId()))
                .thenReturn(Optional.of(time));

        assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        time.getId()
                )
        );
    }

    @Test
    void 현재보다_이후_시간으로_예약할_수_있다() {
        Time time = new Time(1L, NOW.plusMinutes(1));

        when(timeRepository.findById(time.getId()))
                .thenReturn(Optional.of(time));

        Reservation savedReservation = new Reservation(1L, NAME, TODAY, time);

        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(savedReservation);

        Reservation result = reservationService.create(NAME, TODAY, time.getId());

        ArgumentCaptor<Reservation> captor =
                ArgumentCaptor.forClass(Reservation.class);

        verify(reservationRepository).save(captor.capture());

        Reservation requestedReservation = captor.getValue();

        assertSame(savedReservation, result);
        assertEquals(NAME, requestedReservation.getName());
        assertEquals(TODAY, requestedReservation.getDate());
        assertEquals(time.getTime(), requestedReservation.getTime().getTime());
    }

    @Test
    void 중복_예약을_생성하면_예외가_발생한다() {
        Time time = new Time(1L, NOW.plusHours(2));

        when(timeRepository.findById(time.getId()))
                .thenReturn(Optional.of(time));

        when(reservationRepository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                time.getId()
        )).thenReturn(true);

        assertThrows(
                DuplicateReservationException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        time.getId()
                )
        );
    }

    @Test
    void 존재하는_예약을_삭제할_수_있다() {
        Long id = 1L;

        when(reservationRepository.deleteById(id))
                .thenReturn(true);

        reservationService.delete(id);

        verify(reservationRepository).deleteById(id);
    }

    @Test
    void 존재하지_않는_예약을_삭제하면_예외가_발생한다() {
        when(reservationRepository.deleteById(NON_EXISTENT_ID))
                .thenReturn(false);

        assertThrows(
                ReservationNotFoundException.class,
                () -> reservationService.delete(NON_EXISTENT_ID)
        );
    }

    @Test
    void 존재하지_않는_시간으로_예약하면_예외가_발생한다() {
        when(timeRepository.findById(NON_EXISTENT_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                TimeNotFoundException.class,
                () -> reservationService.create(
                        NAME,
                        TODAY,
                        NON_EXISTENT_ID
                )
        );
    }
}
