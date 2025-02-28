package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.global.exception.BadRequestException;
import roomescape.global.exception.ExceptionMessage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeTest {

    @Test
    void 시간을_입력하지_않을시_예외가_발생한다() {
        assertThatThrownBy(() -> new Time(null))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_TIME.getMessage());
    }
}
