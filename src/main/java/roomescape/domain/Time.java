package roomescape.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class Time {

    private int id;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public Time() {

    }

    public Time(int id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTIme(LocalTime time) {
        this.time = time;
    }
}
