package roomescape.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String time;

    public Time(Long id, String time){
        this.id = id;
        this.time = time;
    }
    public Long getId(){
        return id;
    }

    public String getTime(){
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTime(String time){
        this.time = time;
    }
}
