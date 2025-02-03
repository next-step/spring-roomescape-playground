package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private int id;
    private String name;
    private LocalDate date;  // 날짜만 저장
    private LocalTime time;  // 시간만 저장

    public Reservation(int id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public int getId() {
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

}
