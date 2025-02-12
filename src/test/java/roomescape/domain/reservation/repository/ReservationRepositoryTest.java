package roomescape.domain.reservation.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.domain.Reservation;

@JdbcTest
class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getReservations_테스트() {
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);

        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                "15:41");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "커찬", "2024-08-05",
                "15:42");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "망고", "2025-08-05",
                "15:43");
        List<Reservation> reservations = reservationRepository.getReservations();

        Assertions.assertThat(3).isEqualTo(reservations.size());
        Assertions.assertThat(reservations)
                .extracting(Reservation::getName)
                .containsExactly("브라운", "커찬", "망고");
    }
}
