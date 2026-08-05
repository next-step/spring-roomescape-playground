package roomescape.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ReservationTest {
    @Test
    void 이름이_정상이면_예약이_생성된다() {
        //Given
        String name = "이준환";
        //When & Then
        assertDoesNotThrow(() -> new Reservation(1, name, LocalDate.of(2026, 8, 5), LocalTime.of(10, 0)));
    }

    @Test
    void 이름이_null이면_예외가_발생한다() {
        //Given
        String name = null;
        //When & Then
        assertThatThrownBy(() -> new Reservation(1, name, LocalDate.of(2026, 8, 5), LocalTime.of(10, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 입력해주세요");
    }

    @Test
    void 이름이_공백이면_예외가_발생한다() {
        //Given
        String name = " ";
        //When & Then
        assertThatThrownBy(() -> new Reservation(1, name, LocalDate.of(2026, 8, 5), LocalTime.of(10, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 입력해주세요");
    }
}
