package roomescape.controller.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.global.exception.InvalidValueException;

class RequestTimeTest {

    @Test
    @DisplayName("올바르지 않은 시간 형식의 경우 예외가 발생한다.")
    void shouldThrowException_whenInvalidTimeFormat() {
        // given // when // then
        assertThatThrownBy(() -> new RequestTime("33:99"))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("시간(시:분)형식에 맞게 입력해 주세요. ex) 15:30");
    }
}
