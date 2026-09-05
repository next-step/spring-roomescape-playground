package roomescape.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;


    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(String name, LocalDate date, LocalTime time) {
        validateReservationArgument(name, date, time);
        return new Reservation(null, name, date, time);
    }

    public static Reservation restore(Long id, String name, LocalDate date, LocalTime time) {
        return new Reservation(id, name, date, time);
    }

    public Reservation withId(Long id) {
        return new Reservation(id, this.name, this.date, this.time);
    }


    public static void validateReservationArgument(String name, LocalDate date, LocalTime time) {
        LocalDate todayDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비워져있을 수 없습니다.");
        }
        if (date == null) {
            throw new IllegalArgumentException("날짜는 비워져있을 수 없습니다.");
        }
        if (date.isBefore(todayDate)) {
            throw new IllegalArgumentException("지난 날짜를 예약할 수 없습니다.");
        }
        if (time == null) {
            throw new IllegalArgumentException("시간은 비워져있을 수 없습니다.");
        }
        if (date.isEqual(todayDate) && time.isBefore(nowTime)) {
            throw new IllegalArgumentException("지난 시간을 예약할 수 없습니다.");
        }
    }
    public Long getId() { return id; }

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

