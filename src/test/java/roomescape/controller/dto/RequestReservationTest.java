package roomescape.controller.dto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestReservationTest {

    @Test
    @DisplayName("예약에 필요한 데이터가 비어있을 경우 예외가 발생한다.")
    void shouldThrowException_whenEmptyReservationNameOfData() {
        // given // when // then
        assertThatThrownBy(() -> new RequestReservation("2025-06-30", "", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예약하기 위한 데이터(이름, 날짜, 시간)를 모두 입력해 주세요.");
    }

    @Test
    @DisplayName("올바르지 않은 날짜 형식의 경우 예외가 발생한다.")
    void shouldThrowException_whenInvalidDateFormat() {
        // given // when // then
        assertThatThrownBy(() -> new RequestReservation("2030-13-99", "dd", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜(년도-월-일)형식에 맞게 입력해 주세요. ex) 2020-12-31");
    }
}
