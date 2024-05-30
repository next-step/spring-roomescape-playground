package roomescape.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDomain {
    private Long id;
    private String name;
    private String date;
    private TimeDomain time;
    public ReservationDomain() {
    }
    public ReservationDomain(String name, String date, TimeDomain time) {
        this.id= null;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public ReservationDomain(Long id, String name, String date, TimeDomain time) {
        this.id= id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
