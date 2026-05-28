package roomescape.domain;

import java.time.LocalDate;
import roomescape.exception.BadRequestException;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    public Reservation(Long id, String name, LocalDate date, Time time) {
        validateName(name);

        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isSameSchedule(Reservation other) {
        return this.date.equals(other.date)
                && this.time.equals(other.time);
    }

    private void validateName(String name) {
        if (name.length() > 10) {
            throw new BadRequestException("이름은 10자 이하만 가능합니다.");
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