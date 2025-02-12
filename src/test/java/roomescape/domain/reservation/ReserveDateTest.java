package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import roomescape.common.error.exception.BusinessException;

class ReserveDateTest {

    @Test
    void 예약이_오늘날짜보다_전이라면_에러throrw() {
        //given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // when
        Throwable catchThrow = catchThrowable(() -> new ReserveDate(yesterday));
        // then
        System.out.println(catchThrow);
        assertThat(catchThrow).isInstanceOf(BusinessException.class);
    }
}
