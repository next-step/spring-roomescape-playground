package roomescape.domain;

import roomescape.exception.InvalidReservationException;

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time) {
        validate(name,date,time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, String date, String time) {
        if (isNullOrBlank(name)) {
            throw new InvalidReservationException("예약자 이름은 필수입니다.");
        }
        if (isNullOrBlank(date)) {
            throw new InvalidReservationException("예약 날짜는 필수입니다.");
        }
        if (isNullOrBlank(time)) {
            throw new InvalidReservationException("예약 시간은 필수입니다.");
        }
    }

    private boolean isNullOrBlank(String value) {
        return value == null || value.trim().isEmpty();
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}
