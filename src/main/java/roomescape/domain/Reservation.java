package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class Reservation {

  private Long id;
  private String name;
  private String date;
  private Time time;

  public void generateId(Long id) {
    this.id = id;
  }
}
