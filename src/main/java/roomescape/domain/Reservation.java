package roomescape.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = new ReservationTime(time);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationTime {
        private String time;
    }
}
