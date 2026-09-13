package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(TimeRepository.class)
class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 시간을_저장하고_LocalTime으로_조회한다() {
        ReservationTime saved =
                timeRepository.save(LocalTime.of(9, 30));

        ReservationTime found = timeRepository.findById(saved.getId())
                .orElseThrow();

        assertThat(found.getTime()).isEqualTo(LocalTime.of(9, 30));
    }

    @Test
    void H2는_잘못된_시간_문자열을_거부한다() {
        for (String invalidTime : new String[]{"ab:cd", "99:99"}) {
            assertThatThrownBy(() -> jdbcTemplate.update(
                    "INSERT INTO reservation_time (time) VALUES (?)",
                    invalidTime
            )).isInstanceOf(DataAccessException.class);
        }
    }

    @Test
    void 시간을_시간순으로_비교할_수_있다() {
        timeRepository.save(LocalTime.of(9, 0));
        timeRepository.save(LocalTime.of(11, 0));

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reservation_time WHERE time > ?",
                Integer.class,
                LocalTime.of(10, 0)
        );

        assertThat(count).isEqualTo(1);
    }
}
