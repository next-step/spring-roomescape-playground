package roomescape.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(){
    }

    public Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation toEntity(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }
}
