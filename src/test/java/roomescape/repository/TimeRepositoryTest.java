package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import roomescape.domain.Time;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class TimeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 저장된_시간_목록을_정상적으로_반환한다() {
        // Given
        TimeRepository timeRepository = new TimeRepository(jdbcTemplate);

        timeRepository.save(
                new Time(LocalTime.of(10, 0))
        );

        // When
        List<Time> times = timeRepository.findAll();

        // Then
        assertThat(times).hasSize(1);
        assertThat(times.get(0).getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 시간을_추가하면_id가_정상적으로_부여되고_저장된다() {
        // Given
        TimeRepository timeRepository = new TimeRepository(jdbcTemplate);

        // When
        Time savedTime = timeRepository.save(
                new Time(LocalTime.of(10, 0))
        );

        // Then
        assertThat(savedTime.getId()).isPositive();
    }

    @Test
    void 여러_시간을_추가하면_ID가_순차적으로_부여된다() {
        // Given
        TimeRepository timeRepository = new TimeRepository(jdbcTemplate);

        // When
        Time firstAddedTime = timeRepository.save(
                new Time(LocalTime.of(10, 0))
        );
        Time secondAddedTime = timeRepository.save(
                new Time(LocalTime.of(11, 0))
        );

        // Then
        assertThat(firstAddedTime.getId())
                .isLessThan(secondAddedTime.getId());
    }

    @Test
    void 삭제하려는_id의_시간이_존재하면_삭제에_성공한다() {
        // Given
        TimeRepository timeRepository = new TimeRepository(jdbcTemplate);

        Time time = timeRepository.save(
                new Time(LocalTime.of(10, 0))
        );

        // When
        boolean deleted = timeRepository.deleteById(time.getId());

        // Then
        assertThat(deleted).isTrue();
        assertThat(timeRepository.findAll()).isEmpty();
    }

    @Test
    void 삭제하려는_id의_시간이_존재하지_않으면_삭제에_실패한다() {
        // Given
        TimeRepository timeRepository = new TimeRepository(jdbcTemplate);

        // When
        boolean deleted = timeRepository.deleteById(999);

        // Then
        assertThat(deleted).isFalse();
    }
}
