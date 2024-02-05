package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Time;

public class ReservationDTO {
    private long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public ReservationDTO(String name, LocalDate date, Time time) {
        this.id = 0;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public ReservationDTO(long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public static ReservationDTO toEntity(long id, String name, LocalDate date, Time time){

        return new ReservationDTO(id, name, date, time);

    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getName(){
        return name;
    }

    public Time getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }
}
