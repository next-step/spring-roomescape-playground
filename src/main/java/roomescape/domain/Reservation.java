package roomescape.domain;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id,String name,String date,String time){
        this.id=id;
        this.name=name;
        this.date=date;
        this.time=time;
    }
    public Long getId() { return id; }
    public String getName() { return name; }public String getDate() { return date; }
    public String getTime() { return time; }
}
