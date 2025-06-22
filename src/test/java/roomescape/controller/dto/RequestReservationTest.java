package roomescape.controller.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.global.exception.InvalidReservationException;

class RequestReservationTest {

    @Test
    @DisplayName("입력된 예약 이름이 비어있을 경우 예외가 발생한다.")
    void shouldThrowException_whenEmptyReservationNameOfData() {
        // given // when
        InvalidReservationException exception = assertThrows(
                InvalidReservationException.class,
                () -> new RequestReservation("2025-06-30", "", "12:00")
        );

        // then
        assertEquals("예약하기 위한 데이터(이름, 날짜, 시간)를 모두 입력해 주세요.", exception.getMessage());
    }

    @Test
    @DisplayName("과거의 날짜/시간일 경우 예외가 발생한다.")
    void shouldThrowException_whenReservationIsPast() {
        // given
        String yesterday = String.valueOf(LocalDate.now().minusDays(1));

        // when
        InvalidReservationException exception = assertThrows(
                InvalidReservationException.class,
                () -> new RequestReservation(yesterday, "지윤", "10:00").validatePasted()
        );

        // then
        assertEquals("이미 지난 날짜 및 시간은 예약할 수 없어요.", exception.getMessage());
    }
}
