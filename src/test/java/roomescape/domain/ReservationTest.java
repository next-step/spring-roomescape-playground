package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import roomescape.exception.ReservationInvalidException;

class ReservationTest {
    private final Clock clock = Clock.fixed(Instant.parse("2030-08-05T12:00:00Z"), ZoneOffset.UTC);

    @Test
    void 현재_시간보다_이전이면_예약할_수_없다() {
        assertThatThrownBy(() -> Reservation.create(
                "브라운", LocalDate.of(2030, 8, 5), LocalTime.of(11, 59), clock))
                .isInstanceOf(ReservationInvalidException.class)
                .hasMessage("과거 시간으로 예약할 수 없습니다");
    }

    @Test
    void 현재_시간이면_예약할_수_있다() {
        assertThatCode(() -> Reservation.create(
                "브라운", LocalDate.of(2030, 8, 5), LocalTime.of(12, 0), clock))
                .doesNotThrowAnyException();
    }
}
