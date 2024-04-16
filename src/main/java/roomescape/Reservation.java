package roomescape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Reservation {
  private Long id;
  private final String name;
  private final String date;
  private final String time;
}
