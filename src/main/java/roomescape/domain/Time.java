package roomescape.domain;
import java.time.LocalTime;

public class Time {
    private int id;
    private LocalTime time;
    public Time(int id, LocalTime time){
        this.id = id;
        this.time = time;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public LocalTime getTime(){
        return time;
    }
}
