package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(){}

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.id = 0;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId(){
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

    public void setId(long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }
    public void setTime(LocalTime time){
        this.time = time;
    }

}

