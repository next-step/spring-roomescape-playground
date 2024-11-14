package roomescape.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class Time {
  private Long id;
  private String time;
  private List<Reservation> reservations;

  public void generateId(Long id) {
    this.id = id;
  }
}