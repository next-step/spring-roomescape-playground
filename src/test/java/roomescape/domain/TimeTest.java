package roomescape.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import roomescape.global.exception.BadRequestException;
import roomescape.global.exception.ExceptionMessage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeTest {

    @ParameterizedTest
    @NullAndEmptySource
    void 시간을_입력하지_않을시_예외가_발생한다(String time) {
        assertThatThrownBy(() -> new Time(time))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_TIME.getMessage());
    }
}
