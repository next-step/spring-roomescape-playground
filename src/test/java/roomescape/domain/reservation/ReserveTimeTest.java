package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import roomescape.common.error.exception.BusinessException;

class ReserveTimeTest {

    @Test
    void 예약이_null_이라면_에러throrw() {
        //given
        LocalTime beforeOneSecond = null;
        // when
        Throwable catchThrow = catchThrowable(() -> new ReserveTime(beforeOneSecond));
        // then
        assertThat(catchThrow).isInstanceOf(BusinessException.class);
    }
}
