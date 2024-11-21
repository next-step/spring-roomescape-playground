package roomescape.entity;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private LocalDateTime time;

    public Reservation(Long id, String name, String date, LocalDateTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, String date, LocalDateTime time) {
        this(null, name, date, time);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
