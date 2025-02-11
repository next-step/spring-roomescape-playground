package roomescape.domain.reservation.repository;


import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.domain.Reservation;

@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void getReservations_테스트() {
        List<Reservation> reservations = reservationRepository.getReservations();
        int beforeCount = reservations.size();

        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                "15:40");
        reservations = reservationRepository.getReservations();
        int afterCount = reservations.size();

        Assertions.assertThat(beforeCount + 1).isEqualTo(afterCount);
    }
}
