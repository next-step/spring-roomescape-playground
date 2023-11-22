package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    public Reservation(){
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time){
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, String date, String time){
        this(id, name, LocalDate.parse(date), LocalTime.parse(time));
    }

    public Reservation(String name, LocalDate date, LocalTime time){
        this(null, name, date, time);
    }

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getTime(){
        return time;
    }

    public static Reservation newInstance(Reservation reservation, Long id){
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
