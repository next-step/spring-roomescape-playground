package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationDTO {
    private String name;
    private LocalDate date;
    private LocalTime time;

    public String getName(){
        return name;
    }

    public LocalDate getDate(){
        return date;
    }

    public LocalTime getTime(){
        return time;
    }
}
