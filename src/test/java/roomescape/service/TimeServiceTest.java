package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateCommand;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;
import roomescape.repository.JdbcTimeRepository;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({TimeService.class, JdbcTimeRepository.class})
class TimeServiceTest {
    private static final LocalTime TIME = LocalTime.of(10, 0);

    @Autowired
    private TimeService timeService;

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
}
