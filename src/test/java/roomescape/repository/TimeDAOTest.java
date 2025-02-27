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
    @DisplayName("시간아 DB애 저장이 잘 되는지 확인")
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
    @DisplayName("저장된 시간들을 제대로 조회하는지 확인")
    void 시간들을_조회할_수_있다() {
        // when
        List<Time> times = timeDAO.findTimes();

        // then
        assertThat(times).isNotEmpty();
        assertThat(times).anyMatch(time -> time.equals(savedTime));
    }

    @Test
    @DisplayName("저장된 시간중 하나를 ID로 시간을 조회하는지 확인")
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
    @DisplayName("시간을 삭제할 수 있는 지 확인")
    void 시간을_삭제할_수_있다() {
        // when
        timeDAO.deleteTime(savedTime.getId());

        // then
        assertThatThrownBy(() -> timeDAO.findTime(savedTime.getId()))
            .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("특정 시간이 존재하는지 확인할 수 있는지 확인")
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
