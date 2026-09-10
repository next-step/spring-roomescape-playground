package roomescape.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {
    private final Time time = new Time(1L, LocalTime.of(15, 40));

    @Test
    void 이름이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(1L, null, LocalDate.now(), time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_공백이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(1L, "  ", LocalDate.now(), time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 날짜가_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(1L, "브라운", null, time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시간이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Reservation(1L, "브라운", LocalDate.now(), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
