package roomescape.domain.time;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.common.error.exception.InvalidValueException;

class TimeTest {

    @Test
    void 시간이_Null일_경우_에러를_throw해야한다() {
        Assertions.assertThatThrownBy(() -> new Time(1L, null))
                .isInstanceOf(InvalidValueException.class);
    }

    @Test
    void 시간_객체_생성() {
        //given
        LocalTime localTime = LocalTime.NOON;
        // when
        Time time = new Time(1L, localTime);
        // then
        Assertions.assertThat(time.getTime()).isEqualTo(localTime);
    }
}