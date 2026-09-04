package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.exception.BlankReservationException;

public record ReservationTime(Long id, @JsonFormat(pattern = "HH:mm") LocalTime time) {

  public ReservationTime{
    validate(time);
  }

  private void validate(LocalTime time) {
    if(time == null){
      throw new BlankReservationException("예약시간은 누락될 수 없습니다.");
    }
  }
}