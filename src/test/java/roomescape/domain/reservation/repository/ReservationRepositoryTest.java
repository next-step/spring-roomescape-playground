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
import roomescape.domain.reservationTime.domain.ReservationTime;

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
    void findAll_테스트() {
        jdbcTemplate.update(
                "INSERT INTO reservationTime (id, time) VALUES (?, ?), (?, ?), (?, ?)",
                0L, LocalTime.of(11, 0),
                1L, LocalTime.of(12, 0),
                2L, LocalTime.of(13, 0)
        );
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 0L);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "바바", "2023-08-05", 0L);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "보보", "2023-08-05", 0L);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "커찬", "2024-08-05", 1L);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "망고", "2025-08-05", 2L);
        List<Reservation> reservations = reservationRepository.findAll();

        Assertions.assertThat(reservations.size()).isEqualTo(5);
        Assertions.assertThat(reservations)
                .extracting(Reservation::getName)
                .containsExactly("브라운", "바바", "보보", "커찬", "망고");
    }

    @Test
    void create_테스트() {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(11, 00));
        Reservation reservation = new Reservation("망고", LocalDate.of(2020, 1, 1), reservationTime);

        jdbcTemplate.update("INSERT INTO reservationTime (id, time) values (?,?)", reservationTime.getId(),
                reservationTime.getTime());

        Reservation savedReservation = reservationRepository.create(reservation);
        Map<String, Object> foundReservation = jdbcTemplate.queryForMap("SELECT * FROM reservation WHERE id = ?",
                savedReservation.getId());
        long timeId = (long) foundReservation.get("time_id");
        LocalDate foundDate = ((java.sql.Date) foundReservation.get("date")).toLocalDate();

        Assertions.assertThat(savedReservation).isNotNull();
        Assertions.assertThat(foundReservation.get("name")).isEqualTo(savedReservation.getName());
        Assertions.assertThat(foundDate).isEqualTo(savedReservation.getDate());
        Assertions.assertThat(timeId).isEqualTo(savedReservation.getReservationTime().getId());
    }

    @Test
    void remove_테스트() {
        jdbcTemplate.update("INSERT INTO reservationTime (id, time) VALUES (?, ?)", 0L, LocalTime.of(11, 0));
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "커찬", "2024-08-05", 0L);
        Long id = jdbcTemplate.queryForObject("SELECT id FROM reservation WHERE name = ?", Long.class, "커찬");

        reservationRepository.remove(id);
        Integer afterSize = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        Assertions.assertThat(afterSize).isEqualTo(0);
    }
}
