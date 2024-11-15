package roomescape.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Reservation {

  private Long id;
  private String name;
  private String date;
  private Time time;

  public static Reservation create(Long id, String name, String date, Time time) {
    return new Reservation(id, name, date, time);
  }
}
