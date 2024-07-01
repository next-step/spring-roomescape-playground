package roomescape.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
    @Test
    @DisplayName("필드가 하나라도 null이면 IllegalArgumentException 발생")
    void create() {
        assertThrows(IllegalArgumentException.class, () -> new Reservation(null, "bang", "2024-07-01", "10:00"));
        assertThrows(IllegalArgumentException.class, () -> new Reservation(1L, null, "2024-07-01", "10:00"));
        assertThrows(IllegalArgumentException.class, () -> new Reservation(1L, "bang", null, "10:00"));
        assertThrows(IllegalArgumentException.class, () -> new Reservation(1L, "bang", "2024-07-01", null));
    }

    @Test
    @DisplayName("모든 필드가 정상적으로 입력되면 Reservation 객체 생성")
    void create1() {
        Reservation reservation = assertDoesNotThrow(
                () -> new Reservation(1L, "bang", "2024-07-01", "10:00")
        );
        assertEquals(1L, reservation.getId());
        assertEquals("bang", reservation.getName());
        assertEquals("2024-07-01", reservation.getDate());
        assertEquals("10:00", reservation.getTime());
    }
}
