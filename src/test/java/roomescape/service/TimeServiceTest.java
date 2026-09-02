package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateCommand;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;
import roomescape.repository.JdbcTimeRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({TimeService.class, JdbcTimeRepository.class})
class TimeServiceTest {
    private static final LocalTime TIME = LocalTime.of(10, 0);

    @Autowired
    private TimeService timeService;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 유효한_시간대_요청이면_저장된_시간대를_반환한다() {
        // given
        TimeCreateCommand command = new TimeCreateCommand(TIME);

        // when
        Time time = timeService.createTime(command);

        // then
        assertThat(time.getId()).isNotNull();
        assertThat(time.getStartAt()).isEqualTo(command.startAt());
    }

    @Test
    void 이미_존재하는_시간대이면_예외를_던진다() {
        // given
        TimeCreateCommand firstCommand = new TimeCreateCommand(TIME);
        TimeCreateCommand duplicatedCommand = new TimeCreateCommand(TIME);

        timeService.createTime(firstCommand);

        // when & then
        assertThatThrownBy(() -> timeService.createTime(duplicatedCommand))
                .isInstanceOfSatisfying(
                        TimeException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(TimeErrorCode.TIME_CONFLICT)
                );
    }

    @Test
    void 존재하는_id로_시간대를_삭제한다() {
        // given
        TimeCreateCommand command = new TimeCreateCommand(TIME);
        Time time = timeService.createTime(command);

        // when
        timeService.deleteTime(time.getId());

        // then
        assertThat(timeRepository.findAll())
                .noneMatch(foundTime -> foundTime.getId().equals(time.getId()));
    }

    @Test
    void 존재하지_않는_id로_시간대_삭제_요청_시_예외를_던진다() {
        // given
        Long nonExistingId = -1L;

        // when & then
        assertThatThrownBy(() -> timeService.deleteTime(nonExistingId))
                .isInstanceOfSatisfying(
                        TimeException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(TimeErrorCode.TIME_NOT_FOUND)
                );
    }

    @Test
    void 예약이_존재하는_시간대는_삭제할_수_없다() {
        // given
        Time time = timeService.createTime(new TimeCreateCommand(TIME));
        jdbcTemplate.update("insert into reservations (name, reservation_date, time_id) values (?, ?, ?)",
                "브라운", LocalDate.of(2027, 8, 15), time.getId());

        // when & then
        assertThatThrownBy(() -> timeService.deleteTime(time.getId()))
                .isInstanceOfSatisfying(
                        TimeException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(TimeErrorCode.TIME_IN_USE)
                );
        assertThat(timeRepository.findById(time.getId())).isPresent();
    }
}
