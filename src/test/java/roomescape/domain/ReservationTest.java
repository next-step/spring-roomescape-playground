package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.BlankReservationException;

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
        assertDoesNotThrow(() -> new Reservation(1, name, LocalDate.of(2026, 8, 5), new Time(LocalTime.of(10, 0))));
    }

    @Test
    void 이름이_null이면_예외가_발생한다() {
        //Given
        String name = null;
        //When & Then
        assertThatThrownBy(() -> new Reservation(1, name, LocalDate.of(2026, 8, 5), new Time(LocalTime.of(10, 0))))
                .isInstanceOf(BlankReservationException.class)
                .hasMessage("이름을 입력해주세요");
    }

    @Test
    void 이름이_공백이면_예외가_발생한다() {
        //Given
        String name = " ";
        //When & Then
        assertThatThrownBy(() -> new Reservation(1, name, LocalDate.of(2026, 8, 5), new Time(LocalTime.of(10, 0))))
                .isInstanceOf(BlankReservationException.class)
                .hasMessage("이름을 입력해주세요");
    }

    @Test
    void 날짜가_정상이면_예약이_생성된다() {
        //Given
        LocalDate date = LocalDate.of(2026, 8, 5);
        //When & Then
        assertDoesNotThrow(() -> new Reservation(1, "이준환", date, new Time(LocalTime.of(10, 0))));
    }

    @Test
    void 날짜가_null이면_예외가_발생한다() {
        //Given
        LocalDate date = null;
        //When & Then
        assertThatThrownBy(() -> new Reservation(1, "이준환", date, new Time(LocalTime.of(10, 0))))
                .isInstanceOf(BlankReservationException.class)
                .hasMessage("날짜를 선택해주세요");
    }

    @Test
    void 시간이_정상이면_예약이_생성된다() {
        //Given
        Time time = new Time(LocalTime.of(10, 0));
        //When & Then
        assertDoesNotThrow(() -> new Reservation(1, "이준환", LocalDate.of(2026, 8, 5), time));
    }

    @Test
    void 시간이_null이면_예외가_발생한다() {
        //Given
        Time time = null;
        //When & Then
        assertThatThrownBy(() -> new Reservation(1, "이준환", LocalDate.of(2026, 8, 5), time))
                .isInstanceOf(BlankReservationException.class)
                .hasMessage("시간을 선택해주세요");
    }
}
