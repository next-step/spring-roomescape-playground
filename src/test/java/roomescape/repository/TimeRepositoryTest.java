package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Time;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class TimeRepositoryTest {
    private final static LocalTime TIME = LocalTime.of(10, 0);

    private TimeRepository timeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        timeRepository = new JdbcTimeRepository(jdbcTemplate);
    }

    @Test
    void 시간대를_저장하면_id가_부여되고_목록에_추가된다() {
        // given
        Time time = new Time(TIME);
        int size = timeRepository.findAll().size();

        // when
        Time savedTime = timeRepository.save(time);

        // then
        assertThat(savedTime.getId()).isNotNull();
        assertThat(savedTime.getStartAt()).isEqualTo(TIME);
        assertThat(timeRepository.findAll()).hasSize(size + 1);
    }

    @Test
    void 같은_시간대를_저장하면_예외를_던진다() {
        // given
        Time time = new Time(TIME);
        timeRepository.save(time);

        // when & then
        assertThatThrownBy(() -> timeRepository.save(time))
                .isInstanceOfSatisfying(
                        TimeException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(TimeErrorCode.TIME_CONFLICT)
                );
    }

    @Test
    void 시간대가_이미_존재할_시_true를_반환한다() {
        // given
        Time time = new Time(TIME);

        // when
        timeRepository.save(time);

        // then
        assertThat(timeRepository.existsByStartAt(TIME)).isTrue();
    }

    @Test
    void 시간대가_존재하지_않을_시_false를_반환한다() {
        assertThat(timeRepository.existsByStartAt(TIME)).isFalse();
    }

}
