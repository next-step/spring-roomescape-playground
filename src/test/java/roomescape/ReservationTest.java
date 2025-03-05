package roomescape;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.entity.Reservation;
import roomescape.entity.Time;
import roomescape.exception.InvalidException;

public class ReservationTest {

    @Test
    @DisplayName("예약_객체_생성_테스트")
    void testValidReservation() {

        //given
        Time validTime = new Time(1, LocalDate.now().atTime(12, 0).toLocalTime());
        Reservation reservation = new Reservation(1, "도요", LocalDate.now(), validTime);

        //then
        assertEquals(1, reservation.getId());
        assertEquals("도요", reservation.getName());
        assertEquals(LocalDate.now(), reservation.getDate());
        assertEquals(validTime, reservation.getTime());

    }

    @Test
    @DisplayName("이름이_null인_경우_예외_발생")
    void validNameTest() {
        Time validTime = new Time(1, LocalDate.now().atTime(12, 0).toLocalTime());
        assertThrows(InvalidException.class, () -> new Reservation(1, null, LocalDate.now(), validTime));
    }

    @Test
    @DisplayName("날짜가_null인_경우_예외_발생")
    void validDateTest() {
        Time validTime = new Time(1, LocalDate.now().atTime(12, 0).toLocalTime());
        assertThrows(InvalidException.class, () -> new Reservation(1, "도요", null, validTime));
    }


}
