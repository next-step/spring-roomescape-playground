package roomescape.domain;

import org.springframework.lang.NonNull;


public class Reservation {


import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private int id;
    @NonNull
    private String name;
    @NonNull
    private String date;
    @NonNull
    private String time;
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public String getTime(){
        return time;
    }
    public Reservation(int id,String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
}