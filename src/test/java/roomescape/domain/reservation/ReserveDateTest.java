package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ReserveDateTest {

    @Test
    void 예약이_오늘날짜보다_전이라면_에러throrw() {
        //given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // when
        Throwable catchThrow = catchThrowable(() -> new ReserveDate(yesterday));
        // then
        assertThat(catchThrow).isInstanceOf(IllegalArgumentException.class);
    }
}