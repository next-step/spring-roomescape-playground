package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Time;
import roomescape.exception.InvalidRequestException;

public class ReservationAddRequest {
    private final String name;

    private final LocalDate date;

    private final Time time;

    public ReservationAddRequest(String name, LocalDate date, Time time) throws InvalidRequestException {
        if(name==null || date == null || time == null || name.isEmpty()){
            throw new InvalidRequestException("Invalid Reservation Request");
        }
        if(time.isInvalid()){
            throw new InvalidRequestException("Invalid Time Value");
        }

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
