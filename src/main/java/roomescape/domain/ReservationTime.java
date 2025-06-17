package roomescape.domain;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ReservationTime {
    private final Long id;
    private final LocalTime time;

    public ReservationTime(Long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    public static ReservationTime from(String time) {
        return new ReservationTime(null, parseTime(time));
    }

    private static LocalTime parseTime(String time) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 시간은 비어있을 수 없습니다.");
        }
        try {
            return LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 시간 형식이 올바르지 않습니다. (HH:mm)");
        }
    }

    private void validate(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("[ERROR] 시간은 필수 입력 항목입니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
