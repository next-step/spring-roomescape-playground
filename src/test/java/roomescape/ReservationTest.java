package roomescape;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
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



}
