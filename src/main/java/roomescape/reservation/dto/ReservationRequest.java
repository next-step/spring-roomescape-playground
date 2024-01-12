package roomescape.reservation.dto;

import java.sql.Time;
import lombok.Getter;

@Getter
public class ReservationRequest {

  private String name;
  private String date;
  private Time time;

}
