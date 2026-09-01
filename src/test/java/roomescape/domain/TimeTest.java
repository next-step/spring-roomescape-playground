package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.TimeErrorCode;
import roomescape.exception.TimeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeTest {
    @Test
    void 시작_시간이_비어있으면_예외를_던진다() {
        assertThatThrownBy(() -> new Time(null))
                .isInstanceOfSatisfying(
                        TimeException.class,
                        exception -> assertThat(exception.getErrorCode())
                                .isEqualTo(TimeErrorCode.TIME_INVALID)
                );
    }
}
