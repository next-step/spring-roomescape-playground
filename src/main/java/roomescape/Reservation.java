package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation() { //기본 생성자
    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) { //setter
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }


    //getter
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
    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
