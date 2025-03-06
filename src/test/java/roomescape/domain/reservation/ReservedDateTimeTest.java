package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.domain.reservation.exception.ReservationException;
import roomescape.domain.time.Time;

class ReservedDateTimeTest {

    private final String reservedDate = "2025-02-15";
    private final String reservedTime = "10:55";

    private final ReservedDateTime RESERVED_DATE_TIME =
            new ReservedDateTime(LocalDate.parse(reservedDate), new Time(1L, LocalTime.parse(reservedTime)));

    @Test
    void 생성시에_예약날짜_Null으로_생성될경우_에러() {
        // when
        Throwable catchThrowable = catchThrowable(() -> createReservedDate(LocalDate.parse(reservedDate), null));
        // then
        assertThat(catchThrowable).isInstanceOf(ReservationException.class);
    }

    @Test
    void 생성시에_예약시간_Null으로_생성될경우_에러() {
        // when
        Throwable catchThrowable = catchThrowable(() -> createReservedDate(null, new Time(1L, LocalTime.now())));
        // then
        assertThat(catchThrowable).isInstanceOf(ReservationException.class);
    }

    private ReservedDateTime createReservedDate(LocalDate reservedDate, Time time) {
        return new ReservedDateTime(reservedDate, time);
    }

    @Test
    void 예약된_날짜를_가져오는_테스트() {
        // given
        LocalDate targetReservedDate = RESERVED_DATE_TIME.getReservedDate();
        LocalTime targetReservedTime = RESERVED_DATE_TIME.getTimeAsLocalTime();
        // when
        boolean dateEqualResult = targetReservedDate.isEqual(LocalDate.parse(reservedDate));
        boolean timeEqualResult = targetReservedTime.equals(LocalTime.parse(reservedTime));

        // then
        Assertions.assertAll(
                () -> assertThat(dateEqualResult).isTrue(),
                () -> assertThat(timeEqualResult).isTrue()
        );
    }

    @Test
    void 같은_날짜와_시간으로_생성된_객체_동등성_테스트() {
        // given
        ReservedDateTime newReservedDateTime =
                new ReservedDateTime(LocalDate.parse(reservedDate), new Time(1L, LocalTime.parse(reservedTime)));
        // when
        boolean isEqual = newReservedDateTime.equals(RESERVED_DATE_TIME);
        // then
        assertThat(isEqual).isTrue();
    }
}
