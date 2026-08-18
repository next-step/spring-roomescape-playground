package roomescape.repository;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationRepositoryTest {

    private static final String NAME = "브라운";
    private static final LocalDate TODAY = LocalDate.of(2023, 1, 2);
    private static final LocalTime NOW = LocalTime.of(10, 30);

    private final ReservationRepository repository = new ReservationRepository();

    @Test
    void 저장된_예약_목록을_조회할_수_있다() {
        List<Reservation> reservations = repository.findAll();
        Reservation first = reservations.get(0);

        assertEquals(3, reservations.size());
        assertEquals(1L, first.getId());
        assertEquals("브라운", first.getName());
        assertEquals(LocalTime.of(10, 0), first.getTime());
    }

    @Test
    void 예약을_저장할_수_있다() {
        Reservation reservation = new Reservation(NAME, TODAY, NOW);
        Reservation savedReservation = repository.save(reservation);

        assertEquals(4L, savedReservation.getId());
        assertEquals(NAME, savedReservation.getName());
        assertEquals(TODAY, savedReservation.getDate());
        assertEquals(NOW, savedReservation.getTime());
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_존재하면_true를_반환한다() {
        Reservation reservation = new Reservation(NAME, TODAY, NOW);
        Reservation savedReservation = repository.save(reservation);

        assertTrue(repository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                NOW
        ));
    }

    @Test
    void 동일한_이름_날짜_시간의_예약이_없으면_false를_반환한다() {
        Reservation reservation = new Reservation(NAME, TODAY, NOW);
        Reservation savedReservation = repository.save(reservation);

        assertFalse(repository.existsByNameAndDateAndTime(
                NAME,
                TODAY,
                NOW.plusMinutes(1)
        ));
    }
}
