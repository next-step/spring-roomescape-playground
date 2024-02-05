package roomescape.domain;

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

}

