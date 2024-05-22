package roomescape.Model;

import roomescape.Exception.BadRequestReservationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RequestReservation {

    private String name;
    private String date;
    private String time;
    private LocalDateTime dateTime;

    public RequestReservation(String name,String date,String time){
        this.date=date;
        this.time=time;
        this.name=name;
        validateReservation();
    }

    public void validateReservation(){
        if (name.isEmpty() || date.isEmpty() || time.isEmpty() )
            throw new BadRequestReservationException("이름, 날짜, 시간을 모두 입력하세요.");
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        this.dateTime = LocalDateTime.of(parsedDate, parsedTime);
    }

    public String getName(){
        return  name;
    }

    public String getDate(){
        return  date;
    }

    public String getTime(){
        return  time;
    }
}