package com.cholog.roomescape.roomescape.business.time;

import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.exception.conflict.TimeConflictException;
import com.cholog.roomescape.roomescape.exception.notfound.TimeNotFoundException;
import com.cholog.roomescape.roomescape.repository.ReservationRepository;
import com.cholog.roomescape.roomescape.repository.ReservationRepositoryImpl;
import com.cholog.roomescape.roomescape.repository.TimeRepositoryImpl;
import com.cholog.roomescape.roomescape.service.TimeService;
import com.cholog.roomescape.roomescape.service.TimeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Import({TimeServiceImpl.class, TimeRepositoryImpl.class, ReservationRepositoryImpl.class})
public class TimeServiceTest {

    @Autowired
    private TimeService timeService;

    @Autowired
    private ReservationRepository reservationRepository;

    private LocalTime dummyLocalTime = LocalTime.of(10, 0);

    @AfterEach
    void teardown() {
        this.timeService = null;
    }

    @Test
    @DisplayName("createTime을 호출하면, 시각 엔터티를 반환한다.")
    void createTimeTest() {
        // when
        Time createdTime = timeService.createTime(dummyLocalTime);

        // then
        assertThat(createdTime.getId()).isNotNull();
        assertThat(createdTime.getTime()).isEqualTo(dummyLocalTime);
    }

    @Test
    @DisplayName("이미 존재하는 시각으로 createTime을 호출하면 예외가 발생한다.")
    void createDuplicatedTimeTest() {
        // given
        timeService.createTime(dummyLocalTime);

        // then
        Assertions.assertThrows(
                TimeConflictException.class,

                // when
                () -> timeService.createTime(dummyLocalTime)
        );
        assertThat(timeService.findAllTime().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("findAllTime을 호출하면, 저장되어 있는 모든 시각을 반환한다.")
    void findAllTimeTest() {
        // given
        Time createdTime = timeService.createTime(dummyLocalTime);
        Time isNotSaved = new Time(dummyLocalTime.plusHours(1));

        // when
        List<Time> times = timeService.findAllTime();

        // then
        assertThat(times.contains(createdTime)).isTrue();
        assertThat(times.contains(isNotSaved)).isFalse();
    }

    @Test
    @DisplayName("deleteTime을 호출하면 저장되어 있는 레코드를 삭제한다.")
    void deleteTimeTest() {
        // given
        Time createdTime = timeService.createTime(dummyLocalTime);

        // when
        timeService.deleteTime(createdTime.getId());

        // then
        assertThat(timeService.findAllTime().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("잘못된 ID로 deleteTime을 호출하면 예외가 발생한다.")
    void deleteTimeByIllegalIdTest() {
        // given
        Time createdTime = timeService.createTime(dummyLocalTime);

        // then
        Assertions.assertThrows(
                TimeNotFoundException.class,

                // when
                () -> timeService.deleteTime(createdTime.getId() + 1L)
        );
        assertThat(timeService.findAllTime().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약이 참조하고 있는 시각을 deleteTime으로 삭제하면 예외가 발생한다.")
    void deleteTimeReferencedByReservationTest() {
        // given
        Time createdTime = timeService.createTime(dummyLocalTime);
        reservationRepository.save(new Reservation("Alice", LocalDate.now(), createdTime));

        // then
        Assertions.assertThrows(
                TimeConflictException.class,

                // when
                () -> timeService.deleteTime(createdTime.getId())
        );
        assertThat(timeService.findAllTime().size()).isEqualTo(1);
    }
}
