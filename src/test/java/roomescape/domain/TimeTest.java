package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.BlankTimeException;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TimeTest {

    @Test
    void 시간이_정상이면_시간이_정상적으로_생성된다() {
        // Given
        LocalTime time = LocalTime.of(10, 0);
        // When & Then
        assertDoesNotThrow(() -> new Time(time));
    }

    @Test
    void 아이디와_시간이_정상이면_시간이_정상적으로_생성된다() {
        // Given
        LocalTime time = LocalTime.of(10, 0);
        // When & Then
        Time createdTime = new Time(1, time);
        assertThat(createdTime.getId()).isEqualTo(1);
        assertThat(createdTime.getTime()).isEqualTo(time);
    }

    @Test
    void 시간이_null이면_예외가_발생한다() {
        // Given
        LocalTime time = null;
        // When & Then
        assertThatThrownBy(() -> new Time(time))
                .isInstanceOf(BlankTimeException.class)
                .hasMessage("시간을 입력해주세요.");
    }

    @Test
    void 아이디를_부여한_새_시간을_반환한다() {
        // Given
        LocalTime time = LocalTime.of(10, 0);
        Time original = new Time(time);

        // When
        Time withIdTime = original.withId(5);

        // Then
        assertThat(withIdTime.getId()).isEqualTo(5);
        assertThat(withIdTime.getTime()).isEqualTo(time);
    }
}
