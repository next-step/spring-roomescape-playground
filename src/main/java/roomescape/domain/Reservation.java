package roomescape.domain;

import roomescape.exception.InvalidReservationArgumentException;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final String time;

    private Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(Long id, String name, String date, String time) {
        validate(name, "이름은 비어있을 수 없습니다.");
        validate(date, "날짜는 비어있을 수 없습니다.");
        validate(time, "시간은 비어있을 수 없습니다.");

        return new Reservation(id, name, date, time);
    }

    private static void validate(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidReservationArgumentException(message);
        }
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

    public String getTime() {
        return time;
    }
}
