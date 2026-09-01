package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ReservationRepositoryTest {

    private static final String NAME = "브라운";
    private static final LocalDate TODAY = LocalDate.of(2023, 1, 2);
    private static final LocalTime NOW = LocalTime.of(10, 30);

    private static final Long NON_EXISTENT_ID = 999L;

    private final ReservationRepository reservationRepository = new ReservationRepository(mock(JdbcTemplate.class));

    @Test
    void 저장된_예약_목록을_조회할_수_있다() {
        List<Reservation> reservations = reservationRepository.findAll();
        Reservation first = reservations.get(0);

        assertEquals(3, reservations.size());
        assertEquals(1L, first.getId());
        assertEquals("브라운", first.getName());
        assertEquals(LocalTime.of(10, 0), first.getTime());
    }

    @Test
    void 예약을_저장할_수_있다() {
        Reservation reservation = new Reservation(NAME, TODAY, NOW);
        Reservation savedReservation = reservationRepository.save(reservation);

        assertEquals(4L, savedReservation.getId());
        assertEquals(NAME, savedReservation.getName());
        assertEquals(TODAY, savedReservation.getDate());
        assertEquals(NOW, savedReservation.getTime());
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_존재하면_true를_반환한다() {
        Reservation reservation = new Reservation(NAME, TODAY, NOW);
        Reservation savedReservation = reservationRepository.save(reservation);

        assertTrue(reservationRepository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                NOW
        ));
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_없으면_false를_반환한다() {
        Reservation reservation = new Reservation(NAME, TODAY, NOW);
        Reservation savedReservation = reservationRepository.save(reservation);

        assertFalse(reservationRepository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                NOW.plusMinutes(1)
        ));
    }

    @Test
    void 존재하는_예약_id로_삭제하면_true를_반환한다() {
        LocalTime reservationTime = NOW.plusHours(2);

        Reservation reservation = new Reservation(NAME, TODAY, reservationTime);
        Reservation savedReservation = reservationRepository.save(reservation);
        Long id = savedReservation.getId();

        assertTrue(reservationRepository.deleteById(id));
    }

    @Test
    void 존재하지_않는_예약_id로_삭제하면_false를_반환한다() {
        assertFalse(reservationRepository.deleteById(NON_EXISTENT_ID));
    }
}
