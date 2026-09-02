package roomescape.domain.reservation;

import roomescape.domain.time.Time;
import java.time.LocalDate;


public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation (Long id, String name, LocalDate date, Time time){
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public Time getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public static Reservation toEntity(ReservationRequest request, Long id, Time time) {
        return new Reservation(id, request.getName(), request.getDate(), time);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름을 입력해주세요.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜를 입력해주세요.");
        }
    }

    private void validateTime(Time time) {
        if (time == null) {
            throw new IllegalArgumentException("시간을 입력해주세요.");
        }
    }
}
