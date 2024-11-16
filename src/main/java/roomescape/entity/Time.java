package roomescape.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Time {

  private Long id;

  private String time;

  public static Time create(Long id, String time) {
    return new Time(id, time);
  }
}
