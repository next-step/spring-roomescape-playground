package roomescape.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.global.exception.BadRequestException;
import roomescape.global.exception.ExceptionMessage;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTest {

    @Test
    void 예약을_생성할_수_있다() {
        assertThatCode(() -> new Reservation("김철수", LocalDate.now(), LocalTime.now()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "김", "아야어여오요우유으이이"})
    void 이름_길이가_2보다_작거나_19보다_크면_오류_발생(String name) {
        assertThatThrownBy(() -> new Reservation(name, LocalDate.now(), LocalTime.MIN))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(
                        ExceptionMessage.INVALID_NAME.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"kim", "나자바!", "철수 Kim"})
    void 이름에_한글_외_다른_문자_넣을시_오류_발생(String name) {
        assertThatThrownBy(() -> new Reservation(name, LocalDate.now(), LocalTime.MIN))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(
                        ExceptionMessage.INVALID_NAME.getMessage());
    }

    @Test
    void 필수_인자_입력하지_않을시_오류_발생() {
        assertAll(
                () -> assertThatThrownBy(() -> new Reservation("김철수", null, LocalTime.MIN))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining(ExceptionMessage.INVALID_DATE.getMessage()),

                () -> assertThatThrownBy(() -> new Reservation("김철수", LocalDate.now(), null))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessageContaining(ExceptionMessage.INVALID_TIME.getMessage()));
    }
}
