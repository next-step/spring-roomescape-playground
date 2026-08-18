package roomescape.repository;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationRepositoryTest {

    @Test
    void 저장된_예약_목록을_조회할_수_있다() {
        ReservationRepository repository = new ReservationRepository();
        List<Reservation> reservations = repository.findAll();
        Reservation first = reservations.get(0);

        assertEquals(3, reservations.size());
        assertEquals(1L, first.getId());
        assertEquals("브라운", first.getName());
        assertEquals(LocalTime.of(10, 0), first.getTime());
    }
}
