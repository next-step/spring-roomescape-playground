package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(@JsonFormat(pattern = "HH:mm") LocalTime time) {

  public ReservationTime toDomain(Long id){
    return new ReservationTime(id, time);
  }
}
