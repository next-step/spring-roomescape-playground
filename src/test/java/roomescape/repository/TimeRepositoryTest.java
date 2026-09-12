package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(TimeRepository.class)
public class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Long NON_EXISTENT_ID = 999L;

    @Test
    @Sql(
            scripts = "/time-test-data.sql",
            config = @SqlConfig(encoding = "UTF-8")
    )
    void 저장된_시간_목록을_조회할_수_있다() {
        List<Time> times = timeRepository.findAll();
        Time first = times.get(0);

        assertEquals(1, times.size());
        assertEquals(1L, first.getId());
        assertEquals(LocalTime.of(10, 0), first.getTime());
    }

    @Test
    void 시간을_저장할_수_있다() {
        Time requestTime = new Time(LocalTime.of(10,0));
        Time savedTime = timeRepository.save(requestTime);

        Long savedId = savedTime.getId();

        Time persistedTime = jdbcTemplate.queryForObject(
                "SELECT id, time FROM time WHERE id = ?",
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getObject("time", LocalTime.class)
                ),
                savedId
        );

        assertEquals(savedTime.getId(), persistedTime.getId());
        assertEquals(savedTime.getTime(), persistedTime.getTime());
    }

    @Test
    void 존재하는_시간_id로_삭제하면_true를_반환한다() {
        jdbcTemplate.update(
                "INSERT INTO time (time) VALUES (?)",
                LocalTime.of(10, 0)
        );

        Long id = jdbcTemplate.queryForObject(
                "SELECT id FROM time WHERE time = ?",
                Long.class,
                LocalTime.of(10, 0)
        );

        assertTrue(timeRepository.deleteById(id));

        assertFalse(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM time WHERE id = ?",
                Boolean.class,
                id
        ));
    }

    @Test
    void 존재하지_않는_시간_id로_삭제하면_false를_반환한다() {
        assertFalse(timeRepository.deleteById(NON_EXISTENT_ID));
    }
}
