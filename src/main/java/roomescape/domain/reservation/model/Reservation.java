package roomescape.domain.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Reservation {

    @Setter
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    @Builder
    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isEquals(long id) {
        return this.id == id;
    }
}
