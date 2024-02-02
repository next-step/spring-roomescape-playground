package roomescape.domain;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.LocalTime;

public class Time {
    @NonNull
    private int id;
    @NonNull
    private String time;
    public Time(int id, String time){
        this.id = id;
        this.time = time;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public String getTime(){
        return time;
    }

}
