package roomescape.domain.reservationTime.repository;

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
import roomescape.domain.reservationTime.domain.ReservationTime;

@JdbcTest
public class ReservationTimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private ReservationTimeRepository reservationTimeRepository;

    @BeforeEach
    public void setUp() {
        reservationTimeRepository = new ReservationTimeRepository(jdbcTemplate, dataSource);
    }

    @Test
    void findAll_테스트() {
        jdbcTemplate.update("INSERT INTO reservationTime (time) VALUES (?)", "11:00");
        jdbcTemplate.update("INSERT INTO reservationTime (time) VALUES (?)", "15:00");
        jdbcTemplate.update("INSERT INTO reservationTime (time) VALUES (?)", "16:00");
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        Assertions.assertThat(reservationTimes.size()).isEqualTo(3);
        Assertions.assertThat(reservationTimes)
                .extracting(ReservationTime::getTime)
                .containsExactly(LocalTime.of(11, 00), LocalTime.of(15, 00), LocalTime.of(16, 00));
    }

    @Test
    void create_테스트() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(11, 00));

        ReservationTime savedTime = reservationTimeRepository.create(reservationTime);
        Map<String, Object> foundReservationTime = jdbcTemplate.queryForMap("SELECT * FROM reservationTime WHERE id=?"
                , savedTime.getId());
        LocalTime foundTime = ((java.sql.Time) foundReservationTime.get("time")).toLocalTime();

        Assertions.assertThat(savedTime).isNotNull();
        Assertions.assertThat(foundTime).isEqualTo(savedTime.getTime());
    }

    @Test
    void remove_테스트() {
        jdbcTemplate.update("INSERT INTO reservationTime (time) VALUES (?)", "11:00");
        Long id = jdbcTemplate.queryForObject("SELECT id FROM reservationTime WHERE time = ?", Long.class,
                LocalTime.of(11, 00));

        reservationTimeRepository.remove(id);
        Integer afterSize = jdbcTemplate.queryForObject("SELECT count(1) from reservationTime", Integer.class);

        Assertions.assertThat(afterSize).isEqualTo(0);
    }
}
