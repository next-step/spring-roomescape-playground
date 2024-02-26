package roomescape.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time){
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
