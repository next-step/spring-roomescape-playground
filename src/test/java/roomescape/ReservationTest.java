package roomescape;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {
    @Test
    void 유효한_값으로_예약을_생성한다() {
        // given
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(10, 0);

        // when
        Reservation reservation = new Reservation("브라운", date, time);

        // then
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo(date);
        assertThat(reservation.getTime()).isEqualTo(time);
    }

    @Test
    void 이름이_비어있거나_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("", LocalDate.now(), LocalTime.of(10, 0)))
                .isInstanceOf(InvalidReservationException.class);
        assertThatThrownBy(() -> new Reservation(" ", LocalDate.now(), LocalTime.of(10, 0)))
                .isInstanceOf(InvalidReservationException.class);
    }

    @Test
    void 이름_형식이_올바르지_않은_예약_생성_시_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("브라운1", LocalDate.now(), LocalTime.of(10, 0)))
                .isInstanceOf(InvalidReservationException.class);
        assertThatThrownBy(() -> new Reservation("브라운@", LocalDate.now(), LocalTime.of(10, 0)))
                .isInstanceOf(InvalidReservationException.class);
    }

    @Test
    void 날짜가_비어있는_예약_생성_시_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("브라운", null, LocalTime.of(10, 0)))
                .isInstanceOf(InvalidReservationException.class);
    }

    @Test
    void 시간이_비어있는_예약_생성_시_예외를_던진다() {
        assertThatThrownBy(() -> new Reservation("브라운", LocalDate.now(), null))
                .isInstanceOf(InvalidReservationException.class);
    }
}
