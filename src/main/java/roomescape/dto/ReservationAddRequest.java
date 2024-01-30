package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Time;

public class ReservationAddRequest {
    private final String name;

    private final LocalDate date;

    private final Time time;

    private ReservationAddRequest(){this(null, null, null);}

    public ReservationAddRequest(String name, LocalDate date, Time time){
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public LocalDate getDate(){
        return date;
    }

    public Time getTime(){
        return time;
    }

}
