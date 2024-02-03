package roomescape.domain;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    protected Reservation(Long id, String string, @NotNull(message = "날짜를 입력해야 합니다") LocalDate date, @NotNull(message = "시간을 입력해야 합니다") Long time) {
    }

    public Reservation(String name, LocalDate date, Time time) {
        this.id = null;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation with(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
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

    public Time getTime() {
        return time;
    }
}
