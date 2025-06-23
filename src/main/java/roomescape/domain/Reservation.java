package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.exception.InvalidRequestException;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, LocalTime time) {
        if (name == null || name.isBlank()) {
            throw new InvalidRequestException("이름은 비어있을 수 없습니다.");
        }
        if (date == null) {
            throw new InvalidRequestException("날짜는 비어 있거나 존재하지 않는 날짜일 수 없습니다. 올바른 날짜를 선택해주세요.");
        }
        if (time == null) {
            throw new InvalidRequestException("시간은 비어있을 수 없습니다.");
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

    public LocalTime getTime() {
        return time;
    }
}
