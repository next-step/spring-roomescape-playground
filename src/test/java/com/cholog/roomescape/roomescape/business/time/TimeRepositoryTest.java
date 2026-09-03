package com.cholog.roomescape.roomescape.business.time;

import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.exception.conflict.TimeConflictException;
import com.cholog.roomescape.roomescape.exception.notfound.TimeNotFoundException;
import com.cholog.roomescape.roomescape.repository.ReservationRepository;
import com.cholog.roomescape.roomescape.repository.ReservationRepositoryImpl;
import com.cholog.roomescape.roomescape.repository.TimeRepository;
import com.cholog.roomescape.roomescape.repository.TimeRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Import({TimeRepositoryImpl.class, ReservationRepositoryImpl.class})
public class TimeRepositoryTest {

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private LocalTime dummyTime = LocalTime.of(10, 0);

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
    @DisplayName("이미 저장된 시각을 다시 save()하면, 유니크 제약 위반으로 예외가 발생한다.")
    void testSaveDuplicatedTime() {
        // given
        timeRepository.save(new Time(dummyTime));

        Time duplicated = new Time(dummyTime);

        // when & then
        assertThrows(
                TimeConflictException.class,
                () -> timeRepository.save(duplicated)
        );
        assertThat(timeRepository.findAll().size()).isEqualTo(1);
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
    @DisplayName("findByTime(LocalTime time)을 호출하면, 해당 시각의 객체를 반환한다.")
    void testFindByTime() {
        // given
        Time saved = timeRepository.save(new Time(dummyTime));

        // when
        Time found = timeRepository.findByTime(dummyTime)
                .orElseThrow(TimeNotFoundException::new);

        // then
        assertThat(found).isEqualTo(saved);
    }

    @Test
    @DisplayName("저장된 적 없는 시각으로 findByTime()을 호출하면 빈 Optional을 반환한다.")
    void testFindByTimeNotFound() {
        // given
        timeRepository.save(new Time(dummyTime));

        // when
        Optional<Time> found = timeRepository.findByTime(dummyTime.plusHours(1));

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

    @Test
    @DisplayName("delete의 인자로 저장된 적 없는 id를 가진 객체를 전달하면 조용히 실패한다.")
    void testDeleteByIllegalId() {
        // given
        timeRepository.save(new Time(dummyTime));
        Time dummy = Time.withId(-1L, new Time(dummyTime.plusHours(1)));

        // when
        timeRepository.delete(dummy);

        // then
        assertThat(timeRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약이 참조하고 있는 시각을 delete()하면, 외래 키 제약 위반으로 예외가 발생한다.")
    void testDeleteTimeReferencedByReservation() {
        // given
        Time saved = timeRepository.save(new Time(dummyTime));
        reservationRepository.save(new Reservation("Alice", LocalDate.now(), saved));

        // when & then
        assertThrows(
                TimeConflictException.class,
                () -> timeRepository.delete(saved)
        );
        assertThat(timeRepository.findAll().size()).isEqualTo(1);
    }
}
