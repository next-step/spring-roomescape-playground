package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import roomescape.domain.ReservationTime;

@Getter
@AllArgsConstructor
public class ReservationTimeRequest {
  @JsonFormat(pattern = "HH:mm")
  private final LocalTime time;

  public ReservationTime toDomain(Long id){
    return new ReservationTime(id, time);
  }
}
