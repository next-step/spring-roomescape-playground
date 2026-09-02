package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import roomescape.exception.InvalidReservationException;

class ReservationTest {

    @Test
    void 유효한_값으로_예약을_생성한다() {
        Reservation reservation = Reservation.create(
            1L,
            "브라운",
            "2023-08-05",
            "15:40"
        );

        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getDate()).isEqualTo("2023-08-05");
        assertThat(reservation.getTime()).isEqualTo("15:40");
    }

    @Test
    void 유효하지_않은_값으로_예약을_생성할_수_없다() {
        assertThatThrownBy(() -> Reservation.create(1L, "", "2023-08-05", "15:40"))
            .isInstanceOf(InvalidReservationException.class);
    }
}
