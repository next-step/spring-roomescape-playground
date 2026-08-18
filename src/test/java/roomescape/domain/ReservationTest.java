package roomescape.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTest {

    private static final LocalDate TODAY = LocalDate.of(2023, 1, 2);
    private static final LocalTime NOW = LocalTime.of(10, 30);
    private static final LocalDate FUTURE_DATE = TODAY.plusDays(1);

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private static final Clock FIXED_CLOCK = Clock.fixed(
            LocalDateTime.of(TODAY, NOW).atZone(ZONE_ID).toInstant(),
            ZONE_ID
    );

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"    "})
    void 예약자_이름이_비어있으면_예외가_발생한다(String name) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(
                        1L,
                        name,
                        FUTURE_DATE,
                        LocalTime.of(10, 0),
                        FIXED_CLOCK
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
                        LocalTime.of(10, 0),
                        FIXED_CLOCK
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
                        FUTURE_DATE,
                        null,
                        FIXED_CLOCK
                )
        );
    }

    @Test
    void 과거_날짜로_예약하면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(
                        1L,
                        "브라운",
                        TODAY.minusDays(1),
                        NOW,
                        FIXED_CLOCK
                )
        );
    }

    @Test
    void 오늘_현재보다_이전_시간으로_예약하면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(
                        1L,
                        "브라운",
                        TODAY,
                        NOW.minusMinutes(1),
                        FIXED_CLOCK
                )
        );
    }

    @Test
    void 현재와_같은_분으로_예약하면_예외가_발생한다() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Reservation(
                        1L,
                        "브라운",
                        TODAY,
                        NOW,
                        FIXED_CLOCK
                )
        );
    }

    @Test
    void 현재보다_이후_시간으로_예약할_수_있다() {
        assertDoesNotThrow(
                () -> new Reservation(
                        1L,
                        "브라운",
                        TODAY,
                        NOW.plusMinutes(1),
                        FIXED_CLOCK
                )
        );
    }
}
