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
    private final TimeDAO timeDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TimeDAOTest(@Autowired JdbcTemplate jdbcTemplate) {
        this.timeDAO = new TimeDAO(jdbcTemplate, new TimeRowMapper());
    }

    @Test
    void 시간을_정상적으로_생성할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(10, 0);
        Time newTime = new Time(reservationTime);

        // when
        Time savedTime = timeDAO.createTime(newTime);

        // then
        assertAll(
            () -> assertThat(savedTime.getId()).isNotNull(),
            () -> assertThat(savedTime.getTime()).isEqualTo(newTime.getTime())
        );
    }

    @Test
    void 모든_시간들을_정상적으로_조회할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(10, 0);
        Time savedTime = timeDAO.createTime(new Time(reservationTime));

        // when
        List<Time> allTimes = timeDAO.findTimes();

        // then
        assertThat(allTimes).isNotEmpty();
        assertThat(allTimes).anyMatch(anyTime -> anyTime.equals(savedTime));
    }

    @Test
    void 특정_시간을_정상적으로_조회할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(10, 0);
        Time savedTime = timeDAO.createTime(new Time(reservationTime));

        // when
        Time foundTime = timeDAO.findTime(savedTime.getId());

        // then
        assertAll(
            () -> assertThat(foundTime).isNotNull(),
            () -> assertThat(foundTime.equals(savedTime)).isTrue()
        );
    }

    @Test
    void 시간을_정상적으로_삭제할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(10, 0);
        Time savedTime = timeDAO.createTime(new Time(reservationTime));

        // when
        timeDAO.deleteTime(savedTime.getId());

        // then
        assertThatThrownBy(() -> timeDAO.findTime(savedTime.getId()))
            .isInstanceOf(InvalidValueException.class)
            .hasMessage(ErrorMessage.NO_TIME.getMessage());
    }

    @Test
    void 특정_시간의_존재여부를_확인할_수_있다() {
        // given
        LocalTime reservationTime = LocalTime.of(10, 0);
        Time savedTime = timeDAO.createTime(new Time(reservationTime));

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
