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
import static roomescape.domain.Reservation.MAX_NAME_LENGTH;
import static roomescape.domain.Reservation.MIN_NAME_LENGTH;

class ReservationTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(Instant.parse("2025-02-17T10:00:00Z"), ZoneId.of("UTC"));

    @Test
    void 예약을_생성할_수_있다() {
        // give
        final Time time = new Time(LocalTime.of(13, 0));
        // when & then
        assertThatCode(() -> new Reservation("김철수", LocalDate.of(2025, 2, 19), time))
                .doesNotThrowAnyException();
    }

    @Test
    void 현재보다_과거_시간대의_예약을_생성하면_true를_반환한다() {
        // given
        final Time time = new Time(LocalTime.of(9, 0));
        final Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 17), time);
        // when &  then
        assertThat(reservation.isExpired(FIXED_CLOCK)).isTrue();
    }

    @Test
    void 현재_이후_시간대의_예약을_생성하면_false를_반환한다() {
        // given
        final Time time = new Time(LocalTime.of(11, 0));
        final Reservation reservation = new Reservation("김철수", LocalDate.of(2025, 2, 17), time);
        // when &  then
        assertThat(reservation.isExpired(FIXED_CLOCK)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {MIN_NAME_LENGTH - 1, MAX_NAME_LENGTH + 1})
    void 이름_길이가_범위내_존재하지_않으면_예외가_발생한다(final int nameLength) {
        // given
        final Time time = new Time(LocalTime.of(13, 0));
        // when &  then
        assertThatThrownBy(() -> new Reservation("a".repeat(nameLength), LocalDate.of(2025, 2, 19), time))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_NAME.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"kim", "나자바!", "철수 Kim"})
    void 이름에_한글_외_다른_문자_넣을시_예외가_발생한다(final String name) {
        // given
        final Time time = new Time(LocalTime.of(13, 0));
        // when &  then
        assertThatThrownBy(() -> new Reservation(name, LocalDate.of(2025, 2, 19), time))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_NAME.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 이름을_입력하지_않을시_예외가_발생한다(final String name) {
        // given
        final Time time = new Time(LocalTime.of(13, 0));
        // when &  then
        assertThatThrownBy(() -> new Reservation(name, LocalDate.of(2025, 2, 19), time))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(ExceptionMessage.INVALID_NAME.getMessage());
    }

    @Test
    void 필수_인자_입력하지_않을시_예외가_발생한다() {
        // given
        final Time time = new Time(LocalTime.of(13, 0));
        // when &  then
        assertAll(
                () -> assertThatThrownBy(() -> new Reservation("김철수", null, time))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage(ExceptionMessage.INVALID_DATE.getMessage()),

                () -> assertThatThrownBy(() -> new Reservation("김철수", LocalDate.of(2025, 2, 19), null))
                        .isInstanceOf(BadRequestException.class)
                        .hasMessage(ExceptionMessage.INVALID_TIME.getMessage()));
    }
}
