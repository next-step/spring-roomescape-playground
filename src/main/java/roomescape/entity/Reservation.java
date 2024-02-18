package roomescape.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;
}
