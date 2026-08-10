package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {}

    public Reservation(Long id, String name, String date, String time) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }

        if (date == null || date.isEmpty()) {
            throw new IllegalArgumentException("날짜를 입력해주세요.");
        }
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다.");
        }

        if (time == null || time.isEmpty()) {
            throw new IllegalArgumentException("시간을 입력해주세요.");
        }
        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }
}
