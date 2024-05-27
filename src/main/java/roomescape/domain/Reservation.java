package roomescape.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private TimeDomain time;
    public Reservation() {
    }
    public Reservation(String name, String date, TimeDomain time) {
        this.id= null;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public Reservation(Long id,String name, String date, TimeDomain time) {
        this.id= id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
