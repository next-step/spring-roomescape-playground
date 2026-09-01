package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.ReservationErrorCode;
import roomescape.exception.ReservationException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {
    private static final LocalDate DATE = LocalDate.of(2027, 8, 14);
    private static final LocalTime TIME = LocalTime.of(10, 0);

    @Test
    void 유효한_값으로_예약을_생성한다() {
        // when
        Reservation reservation = new Reservation("브라운", DATE, TIME);

        // then
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(DATE);
        assertThat(reservation.getTime()).isEqualTo(TIME);
    }

    @Test
    void 이름이_비어있거나_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("", DATE, TIME))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_INVALID)
                );
        assertThatThrownBy(() -> new Reservation(" ", DATE, TIME))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_INVALID)
                );
    }

    @Test
    void 이름_형식이_올바르지_않은_예약_생성_시_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("브라운1", DATE, TIME))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_INVALID)
                );
        assertThatThrownBy(() -> new Reservation("브라운@", DATE, TIME))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_INVALID)
                );
    }

    @Test
    void 날짜가_비어있는_예약_생성_시_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("브라운", null, TIME))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_INVALID)
                );
    }

    @Test
    void 시간이_비어있는_예약_생성_시_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("브라운", DATE, null))
                .isInstanceOfSatisfying(
                        ReservationException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(ReservationErrorCode.RESERVATION_INVALID)
                );
    }
}
