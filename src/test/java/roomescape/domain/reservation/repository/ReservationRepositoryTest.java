package roomescape.domain.reservation.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.dto.ReservationRequest;

@JdbcTest
class ReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate, dataSource);
    }

    @Test
    void getReservations_테스트() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05",
                "15:41");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "커찬", "2024-08-05",
                "15:42");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "망고", "2025-08-05",
                "15:43");
        List<Reservation> reservations = reservationRepository.getReservations();

        Assertions.assertThat(reservations.size()).isEqualTo(3);
        Assertions.assertThat(reservations)
                .extracting(Reservation::getName)
                .containsExactly("브라운", "커찬", "망고");
    }

    @Test
    void addReservation_테스트() {
        Reservation reservation = Reservation.newWithoutId("망고", LocalDate.of(2020, 1, 1),
                LocalTime.of(15, 45));

        Reservation savedReservation = reservationRepository.addReservation(reservation);
        Map<String, Object> foundReservation = jdbcTemplate.queryForMap("SELECT * FROM reservation WHERE id = ?",
                savedReservation.getId());
        LocalDate foundDate = ((java.sql.Date) foundReservation.get("date")).toLocalDate();
        LocalTime foundTime = ((java.sql.Time) foundReservation.get("time")).toLocalTime();

        Assertions.assertThat(savedReservation).isNotNull();
        Assertions.assertThat(foundReservation.get("name")).isEqualTo(savedReservation.getName());
        Assertions.assertThat(foundDate).isEqualTo(savedReservation.getDate());
        Assertions.assertThat(foundTime).isEqualTo(savedReservation.getTime());
    }

    @Test
    void removeReservation_테스트() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "커찬", "2024-08-05",
                "15:42");
        Integer id = jdbcTemplate.queryForObject("SELECT id FROM reservation WHERE name = ?", Integer.class, "커찬");

        reservationRepository.removeReservation(id);
        Integer afterSize = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        Assertions.assertThat(afterSize).isEqualTo(0);
    }
}
