package roomescape.controller.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.global.exception.InvalidValueException;

class RequestReservationTest {

    @Test
    @DisplayName("입력된 예약 이름이 비어있을 경우 예외가 발생한다.")
    void shouldThrowException_whenEmptyReservationNameOfData() {
        // given // when // then
        assertThatThrownBy(() -> new RequestReservation("2025-06-30", "", 1L))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("예약하기 위한 데이터(이름, 날짜, 시간)를 모두 입력해 주세요.");
    }
}
