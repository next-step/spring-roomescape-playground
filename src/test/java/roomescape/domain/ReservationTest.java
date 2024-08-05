package roomescape.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

  @Test
  @DisplayName("예약_생성_테스트")
  void 예약_생성_테스트() {
    //given
    Reservation reservation = new Reservation(729L, "나카가와나츠키",
        LocalDate.of(2024, 6, 23), LocalTime.of(10, 0));
    //when
    //then
    assertThat(reservation.getId()).isEqualTo(729L);
    assertThat(reservation.getName()).isEqualTo("나카가와나츠키");
    assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2024, 6, 23));
    assertThat(reservation.getTime()).isEqualTo(LocalTime.of(10, 0));
  }
}
