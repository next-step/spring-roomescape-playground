package roomescape.domain;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

import java.time.LocalDate;

public class Reservation {

    private long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(){}

    public Reservation(String name, LocalDate date, Time time) {
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
    public Time getTime(){
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
    public void setTime(Time time){
        this.time = time;
    }
}

