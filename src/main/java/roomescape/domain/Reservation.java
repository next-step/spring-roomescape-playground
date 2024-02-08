package roomescape.domain;

import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String date;
    @NonNull
    private Time time;
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public Time getTime(){
        return time;
    }
    public Reservation(int id,String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}