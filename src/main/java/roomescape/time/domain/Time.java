package roomescape.time.domain;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class Time {
    private Long id;
    private LocalTime time;

    public Time(){
    }

    public Time(LocalTime time){
        this.time = time;
    }

    public Time(Long id, LocalTime time){
        this.id = id;
        this.time = time;
    }

}