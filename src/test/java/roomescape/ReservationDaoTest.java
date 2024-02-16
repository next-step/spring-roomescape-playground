package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import roomescape.dao.ReservationQueryingDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDTO;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationQueryingDAO reservationQueryingDAO;

    @Test
    void test(){
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

        final List<ReservationDTO> reservations = reservationQueryingDAO.getAllReservations();

        assertThat(reservations.size())
                .isEqualTo(1);
    }
}
