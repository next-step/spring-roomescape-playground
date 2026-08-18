package roomescape.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    "})
    void 예약자_이름이_비어있으면_예외가_발생한다(String name) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(
                        1L,
                        name,
                        LocalDate.of(2023,1,1),
                        LocalTime.of(10, 0)
                )
        );
    }

    @Test
    void 예약_날짜가_null이면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(
                        1L,
                        "브라운",
                        null,
                        LocalTime.of(10, 0)
                )
        );
    }

    @Test
    void 예약_시간이_null이면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(
                        1L,
                        "브라운",
                        LocalDate.of(2023,1,1),
                        null
                )
        );
    }
}
