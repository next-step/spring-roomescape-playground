package com.cholog.roomescape.roomescape.business.time;

import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.exception.TimeNotFoundException;
import com.cholog.roomescape.roomescape.repository.TimeRepository;
import com.cholog.roomescape.roomescape.repository.TimeRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class TimeRepositoryTest {

    private TimeRepository timeRepository;

    private LocalTime dummyTime = LocalTime.of(10, 0);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        timeRepository = new TimeRepositoryImpl(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        timeRepository = null;
    }

    @Test
    @DisplayName("save()를 호출하면, ID를 갖는 객체를 반환한다.")
    void testSave(){
        // given
        Time time = new Time(dummyTime);

        // when
        Time saved = timeRepository.save(time);

        // then
        assertThat(time.getId()).isNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTime()).isEqualTo(time.getTime());
    }

    @Test
    @DisplayName("findAll()을 호출하면, 저장된 모든 레코드를 반환한다.")
    void testFindAll() {
        // given
        Time time = new Time(dummyTime);
        Time unsaved = new Time(dummyTime.plusHours(1));

        Time saved = timeRepository.save(time);

        // when
        List<Time> times = timeRepository.findAll();

        // then
        assertThat(times.size()).isEqualTo(1);
        assertThat(times.contains(saved)).isTrue();
        assertThat(times.contains(unsaved)).isFalse();
        assertThat(times.get(0).getId()).isEqualTo(saved.getId());
        assertThat(times.get(0).getTime()).isEqualTo(saved.getTime());
    }

    @Test
    @DisplayName("findById(Long id)를 호출하면, 해당 id의 객체를 반환한다.")
    public void testFindById() {
        // given
        Time time = new Time(dummyTime);
        Time saved = timeRepository.save(time);

        // when
        Time found = timeRepository.findById(saved.getId())
                .orElseThrow(TimeNotFoundException::new);

        // then
        assertThat(found).isEqualTo(saved);
    }

    @Test
    @DisplayName("저장된 적 없는 id로 findById()를 호출하면 빈 Optional을 반환한다.")
    void testFindByIdNotFound() {
        // given
        Long neverSavedId = -1L;

        // when
        Optional<Time> found = timeRepository.findById(neverSavedId);

        // then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("delete를 올바르게 호출하면, 저장되어 있던 레코드를 지운다.")
    void testDelete() {
        // given
        Time time = new Time(dummyTime);
        Time saved = timeRepository.save(time);

        // when
        timeRepository.delete(saved);

        // then
        assertThat(timeRepository.findAll().size()).isEqualTo(0);
    }
}
