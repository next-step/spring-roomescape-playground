package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    
    private final Long id;
    
    private final String name;
    
    private final LocalDate date;
    
    private final LocalTime time;

    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "이름은 필수입니다.");
        this.date = Objects.requireNonNull(date, "예약 날짜는 필수 입니다.");
        this.time = Objects.requireNonNull(time, "예약 시간은 필수입니다.");
    }

    public static Reservation ofNew(String name, LocalDate date, LocalTime time) {
        return new Reservation(null, name, date, time);
    }
    
    public static Reservation ofExist(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
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
}
