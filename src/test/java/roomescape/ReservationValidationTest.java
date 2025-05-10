package roomescape;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;

public class ReservationValidationTest {

    @Test
    void 유효하지_않은_이름이면_예외발생() {
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, " ", LocalDate.now().plusDays(1), LocalTime.of(10, 0))
        );
    }

    @Test
    void 과거_날짜면_예외발생() {
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, "브라운", LocalDate.now().minusDays(1), LocalTime.of(10, 0))
        );
    }

    @Test
    void 날짜가_null이면_예외발생() {
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, "브라운", null, LocalTime.of(10, 0))
        );
    }

    @Test
    void 시간이_null이면_예외발생(){
        assertThrows(InvalidReservationException.class, () ->
                new Reservation(1, "브라운", LocalDate.now().plusDays(1), null)
        );
    }
}
