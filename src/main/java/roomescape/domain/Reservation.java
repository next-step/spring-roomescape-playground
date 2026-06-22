package roomescape.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private String date;
<<<<<<< HEAD
    private Time time;
=======
    private String time;
>>>>>>> upstream/hapdaypy
}
