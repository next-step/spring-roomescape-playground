package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Time;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@Import(TimeRepository.class)
class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @Test
    void 시간을_추가한다() {
        // given
        Time time = new Time("13:00");
        // when
        Time savedTime = timeRepository.save(time);
        // then
        assertAll(
                () -> assertThat(savedTime.getId()).isNotNull(),
                () -> assertThat(savedTime.getTime()).isEqualTo("13:00")
        );
    }

    @Test
    void 해당_시간이_이미_존재하면_true를_반환한다() {
        // given
        Time time = new Time("13:00");
        Time savedTime = timeRepository.save(time);
        // when
        boolean exists = timeRepository.existsByTime(savedTime.getTime());
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 해당_시간이_존재하지_않으면_false를_반환한다() {
        // given
        Time time = new Time("13:00");
        timeRepository.save(time);
        String timeToSave = "14:00";
        // when
        boolean exists = timeRepository.existsByTime(timeToSave);
        // then
        assertThat(exists).isFalse();
    }

    @Test
    void 아이디를_통해_특정_시간을_조회한다() {
        // given
        Time time = new Time("13:00");
        Time savedTime = timeRepository.save(time);
        // when
        Optional<Time> foundTime = timeRepository.findById(savedTime.getId());
        // then
        assertThat(foundTime)
                .hasValueSatisfying(timeResult -> assertAll(
                        () -> assertThat(timeResult.getId()).isEqualTo(savedTime.getId()),
                        () -> assertThat(timeResult.getTime()).isEqualTo(savedTime.getTime())
                ));
    }

    @Test
    void 저장한_모든_시간을_조회한다() {
        // given
        Time time1 = new Time("13:00");
        Time time2 = new Time("14:00");
        timeRepository.save(time1);
        timeRepository.save(time2);
        // when
        List<Time> times = timeRepository.findAll();
        // then
        assertThat(times).hasSize(2);
    }

    @Test
    void 아이디를_통해_특정_시간을_삭제한다() {
        // given
        Time time = new Time("13:00");
        Time savedTime = timeRepository.save(time);
        // when
        timeRepository.deleteById(savedTime.getId());
        // then
        Optional<Time> foundTime = timeRepository.findById(savedTime.getId());
        assertThat(foundTime).isEmpty();
    }
}
