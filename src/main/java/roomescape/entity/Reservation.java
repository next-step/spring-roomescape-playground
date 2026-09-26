package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() {
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        if (!isName(name) || !isDate(date) || !isTime(time)) {
            throw new IllegalArgumentException("값의 문제가 있습니다.");
        }
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public boolean isName(String name){
        return name != null && !name.isBlank();
    }

    public boolean isDate(LocalDate date){
        return date != null;
    }

    public boolean isTime(LocalTime time){
        return time != null;
    }
}
