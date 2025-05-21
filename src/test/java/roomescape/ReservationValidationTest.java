package roomescape;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.InvalidReservationException;

public class ReservationValidationTest {

    @Test
    void 유효하지_않은_이름이면_예외발생() {
        Time time = new Time(null, "10:00");
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, " ", LocalDate.now().plusDays(1), time)
        );
    }

    @Test
    void 과거_날짜면_예외발생() {
        Time time = new Time(null, "10:00");
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, "브라운", LocalDate.now().minusDays(1), time)
        );
    }

    @Test
    void 날짜가_null이면_예외발생() {
        Time time = new Time(null, "19:00");
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, "브라운", null, time)
        );
    }

    @Test
    void 시간이_null이면_예외발생(){
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, "브라운", LocalDate.now().plusDays(1), null)
        );
    }
}
