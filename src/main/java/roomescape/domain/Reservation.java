package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validateName(name);
        validateDate(date);
        validateTime(time);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isPast() {
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time.getTime());
        return reservationDateTime.isBefore(LocalDateTime.now());
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        if (name.length() > 10) {
            throw new IllegalArgumentException("이름은 10자를 초과할 수 없습니다.");
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜는 필수입니다.");
        }
    }

    private void validateTime(Time time) {
        if (time == null) {
            throw new IllegalArgumentException("시간은 필수입니다.");
        }
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