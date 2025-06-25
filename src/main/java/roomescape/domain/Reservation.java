package roomescape.domain;

import roomescape.exception.InvalidTimeException;

public class Reservation {
    private final Long id;
    private final String name;
    private final String date;
    private final Time time;

    public Reservation(Long id, String name, String date, Time time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, String date, Time time) {
        if (time == null || name == null || name.isBlank() || date == null || date.isBlank()) {
            throw new InvalidTimeException("이름, 날짜, 시간은 필수입니다.");
        }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public Time getTime() { return time; }
}
