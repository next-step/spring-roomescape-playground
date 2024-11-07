package roomescape.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {
  public static class NotFoundReservationException extends RuntimeException{}

  private Long id;
  private String name;
  private String date;
  private Time time;

  public Reservation(Long id, String name, String date) {
    this.id = id;
    this.name = name;
    this.date = date;
  }
}
