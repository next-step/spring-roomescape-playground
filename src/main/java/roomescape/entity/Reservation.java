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
        validateName(name);
        validateDate(date);
        validateTime(time);
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

    public void validateName(String name){
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 적어주세요.");
        }
    }
    public void validateDate(LocalDate date){
        if (date == null) {
            throw new IllegalArgumentException("날짜를 적어주세요.");
        }
    }
    public void validateTime(LocalTime time){
        if (time == null) {
            throw new IllegalArgumentException("시간을 적어주세요.");
        }
    }
}
