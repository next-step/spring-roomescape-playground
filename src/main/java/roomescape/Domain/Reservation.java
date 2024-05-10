package roomescape.Domain;


import java.sql.Date;
import java.sql.Time;


public class Reservation {

    private Integer id;
    private String name;
    private String time;
    private String date;

    public Reservation(Integer id,String name,String date,String time) {
        this.id=id;
        this.name = name;
        this.time=time;
        this.date=date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
