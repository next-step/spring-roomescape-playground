package roomescape.domain;

import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private LocalDate date;
    @NonNull
    private LocalTime time;
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime(){
        return time;
    }
    public Reservation(int id,String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}