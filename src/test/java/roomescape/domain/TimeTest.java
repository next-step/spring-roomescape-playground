package roomescape.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimeTest {

    @Test
    void 시간이_null이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Time(1L, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
