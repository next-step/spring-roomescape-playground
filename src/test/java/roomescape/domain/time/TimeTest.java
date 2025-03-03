package roomescape.domain.time;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import roomescape.common.error.exception.InvalidValueException;

class TimeTest {

    @Test
    void 시간이_Null일_경우_에러를_throw해야한다() {
        assertThatThrownBy(() -> new Time(1L, null))
                .isInstanceOf(InvalidValueException.class);
    }

}
