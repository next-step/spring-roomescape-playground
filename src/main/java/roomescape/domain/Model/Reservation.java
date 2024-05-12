package roomescape.domain.Model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

}
