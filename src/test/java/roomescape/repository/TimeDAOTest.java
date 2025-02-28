package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Time;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.mapper.TimeRowMapper;

@JdbcTest
public class TimeDAOTest {
    private TimeDAO timeDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Time savedTime;

    @BeforeEach
    void setUp() {
        timeDAO = new TimeDAO(jdbcTemplate, new TimeRowMapper());
        savedTime = timeDAO.createTime(new Time(LocalTime.of(10, 0)));
    }

    @Test
    void 시간을_생성할_수_있다() {
        // given
        Time newTime = new Time(LocalTime.of(12, 0));

        // when
        Time saved = timeDAO.createTime(newTime);

        // then
        assertAll(
            () -> assertThat(saved.getId()).isNotNull(),
            () -> assertThat(saved.getTime()).isEqualTo(newTime.getTime())
        );
    }

    @Test
    void 시간들을_조회할_수_있다() {
        // when
        List<Time> times = timeDAO.findTimes();

        // then
        assertThat(times).isNotEmpty();
        assertThat(times).anyMatch(time -> time.equals(savedTime));
    }

    @Test
    void 시간을_조회할_수_있다() {
        // when
        Time foundTime = timeDAO.findTime(savedTime.getId());

        // then
        assertAll(
            () -> assertThat(foundTime).isNotNull(),
            () -> assertThat(foundTime.equals(savedTime)).isTrue()
        );
    }

    @Test
    void 시간을_삭제할_수_있다() {
        // when
        timeDAO.deleteTime(savedTime.getId());

        // then
        assertThatThrownBy(() -> timeDAO.findTime(savedTime.getId()))
            .isInstanceOf(InvalidValueException.class)
            .hasMessage(ErrorMessage.NO_TIME.getMessage());
    }

    @Test
    void 특정_시간이_존재한다() {
        // when
        boolean exists = timeDAO.existsTime(savedTime.getTime());
        boolean notExists = timeDAO.existsTime(LocalTime.of(15, 0));

        // then
        assertAll(
            () -> assertThat(exists).isTrue(),
            () -> assertThat(notExists).isFalse()
        );
    }
}
