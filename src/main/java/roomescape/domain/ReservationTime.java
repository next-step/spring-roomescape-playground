package roomescape.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.Getter;
import roomescape.exception.BlankReservationException;

@Getter
public class ReservationTime {
  private final Long id;
  @JsonFormat(pattern = "HH:mm")
  private final LocalTime time;

  public ReservationTime(Long id, LocalTime time) {
    validate(time);
    this.id = id;
    this.time = time;
  }

  private void validate(LocalTime time) {
    if(time == null){
      throw new BlankReservationException("예약시간은 누락될 수 없습니다.");
    }
  }
}