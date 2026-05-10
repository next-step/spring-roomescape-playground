package roomescape;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;
}
