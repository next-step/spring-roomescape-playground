package roomescape;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.reservation.entity.Reservation;

public class ReservationTest {
    @Test
    @DisplayName("이름이 null이면 예외 발생")
    void throwExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reservation(1L, null, LocalDate.now().plusDays(1), LocalTime.of(10, 0))
        );
    }

    @Test
    @DisplayName("이름이 공백이면 예외 발생")
    void throwExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reservation(1L, "   ", LocalDate.now().plusDays(1), LocalTime.of(10, 0))
        );
    }

    @Test
    @DisplayName("과거 날짜면 예외 발생")
    void throwExceptionWhenDateIsPast() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reservation(1L, "홍길동", LocalDate.now().minusDays(1), LocalTime.of(10, 0))
        );
    }

    @Test
    @DisplayName("당일이고 과거 시간이면 예외 발생")
    void throwExceptionWhenTimeIsPastToday() {
        LocalDate today = LocalDate.now();
        LocalTime pastTime = LocalTime.now().minusMinutes(1);
        assertThrows(IllegalArgumentException.class, () ->
                new Reservation(1L, "홍길동", today, pastTime)
        );
    }

    @Test
    @DisplayName("시간이 null이면 예외 발생")
    void throwExceptionWhenTimeIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reservation(1L, "홍길동", LocalDate.now().plusDays(1), null)
        );
    }
}
