package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.exception.InvalidReservationException;

class ReservationControllerTest {

    @Test
    void 요청_검증이_성공한_후_ID를_발급한다() {
        ReservationController controller = new ReservationController(new JdbcTemplate());
        ReservationRequest invalidRequest = new ReservationRequest("", "2023-08-05", "15:40");
        ReservationRequest validRequest = new ReservationRequest("브라운", "2023-08-05", "15:40");

        assertThatThrownBy(() -> controller.createReservation(invalidRequest))
            .isInstanceOf(InvalidReservationException.class);

        ResponseEntity<Reservation> response = controller.createReservation(validRequest);

        assertThat(response.getHeaders().getLocation()).isEqualTo(URI.create("/reservations/1"));
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }
}
