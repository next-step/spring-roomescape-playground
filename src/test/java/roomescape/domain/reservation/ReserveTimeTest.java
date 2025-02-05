package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReserveTimeTest {
    @Test
    void 예약이_지금시간보다_전이라면_에러throrw() {
        //given
        LocalTime beforeOneSecond = LocalTime.now().minusSeconds(1);
        // when
        Throwable catchThrow = catchThrowable(() -> new ReserveTime(beforeOneSecond));
        // then
        assertThat(catchThrow).isInstanceOf(IllegalArgumentException.class);
    }
}