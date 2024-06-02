package roomescape.Model;

import java.sql.Time;

public class Reservation {

    private Long id;
    private String name;
    private String date;
    private ReservationTime time;

    public Reservation(Long id,String name,String date,ReservationTime time){
        this.id=id;
        this.name = name;
        this.time=time;
        this.date=date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ReservationTime getTime(){
        return time;
    }

    public String getDate(){
        return date;
    }
}