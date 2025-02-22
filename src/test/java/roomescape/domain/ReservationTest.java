package roomescape.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.global.exception.BadRequestException;
import roomescape.global.exception.ExceptionMessage;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReservationTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2025-02-17T10:00:00Z"), ZoneId.of("UTC"));

    @Test
    void 예약을_생성할_수_있다() {
        assertThatCode(() -> new Reservation("김철수", LocalDate.of(2025, 2, 19), LocalTime.of(13, 0)))
                .doesNotThrowAnyException();
    }

    @Test
    void 현재보다_과거_시간대의_예약을_생성하면_true를_반환한다() {
        // given
        Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 17), LocalTime.of(9, 0));
        // when &  then
        assertThat(reservation.isExpired(FIXED_CLOCK)).isTrue();
    }

    @Test
    void 현재_이후_시간대의_예약을_생성하면_false를_반환한다() {
        // given
        Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 17), LocalTime.of(11, 0));
        // when &  then
        assertThat(reservation.isExpired(FIXED_CLOCK)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "김", "아야어여오요우유으이이"})
    void 이름_길이가_2보다_작거나_10보다_크면_예외가_발생한다(String name) {
        assertThatThrownBy(() -> new Reservation(name, LocalDate.of(2025, 2, 19), LocalTime.of(13, 0)))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_NAME.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"kim", "나자바!", "철수 Kim"})
    void 이름에_한글_외_다른_문자_넣을시_예외가_발생한다(String name) {
        assertThatThrownBy(() -> new Reservation(name, LocalDate.of(2025, 2, 19), LocalTime.of(13, 0)))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_NAME.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 30, 55})
    void 시간이_정각_단위가_아니면_예외가_발생한다(int time) {
        assertThatThrownBy(() -> new Reservation("김철수", LocalDate.of(2025, 2, 19), LocalTime.of(13, time)))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_TIME.getMessage());
    }

    @Test
    void 필수_인자_입력하지_않을시_예외가_발생한다() {
        assertAll(
                () -> assertThatThrownBy(() -> new Reservation("김철수", null, LocalTime.of(13, 0)))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage(ExceptionMessage.INVALID_DATE.getMessage()),

                () -> assertThatThrownBy(() -> new Reservation("김철수", LocalDate.of(2025, 2, 19), null))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage(ExceptionMessage.INVALID_TIME.getMessage()));
    }
}
