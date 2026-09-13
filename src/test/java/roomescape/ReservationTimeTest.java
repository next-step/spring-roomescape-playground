package roomescape;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTimeTest {

    @Test
    void 유효한_예약_시간을_생성한다() {
        ReservationTime time =
                new ReservationTime(1L, LocalTime.of(10, 0));

        assertThat(time.getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 시간이_없으면_생성할_수_없다() {
        assertThatThrownBy(() -> new ReservationTime(1L, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("예약 시간은 필수입니다.");
    }

    @Test
    void 응답은_시와_분_형식을_유지한다() {
        ReservationTime time =
                new ReservationTime(1L, LocalTime.MIDNIGHT);

        ReservationTimeResponse response =
                ReservationTimeResponse.from(time);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTime()).isEqualTo("00:00");
    }
}
