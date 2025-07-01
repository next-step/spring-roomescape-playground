package roomescape.controller.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestTimeTest {

    @Test
    @DisplayName("시간이 비어있을 경우 예외가 발생한다.")
    void shouldThrowException_whenEmptyTime() {
        // given // when // then
        assertThatThrownBy(() -> new RequestTime(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간을 입력해 주세요.");
    }

    @Test
    @DisplayName("올바르지 않은 시간 형식의 경우 예외가 발생한다.")
    void shouldThrowException_whenInvalidTimeFormat() {
        // given // when // then
        assertThatThrownBy(() -> new RequestTime("33:99"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간(시:분)형식에 맞게 입력해 주세요. ex) 15:30");
    }
}
