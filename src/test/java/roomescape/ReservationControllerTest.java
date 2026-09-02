package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.exception.InvalidReservationException;

class ReservationControllerTest {

    @Test
    void 잘못된_요청은_DB에_저장하기_전에_검증한다() {
        ReservationController controller = new ReservationController(new JdbcTemplate());
        ReservationRequest invalidRequest = new ReservationRequest("", "2023-08-05", "15:40");

        assertThatThrownBy(() -> controller.createReservation(invalidRequest))
            .isInstanceOf(InvalidReservationException.class);
    }
}
