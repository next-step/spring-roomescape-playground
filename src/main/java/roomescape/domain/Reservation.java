package roomescape.domain;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;

public class Reservation {
    private static final Pattern NAME_FORMAT = Pattern.compile("^[가-힣]+$");

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        validateName(name);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(String name, LocalDate date, Time time) {
        this(null, name, date, time);
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

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidValueException(ErrorMessage.INVALID_NAME.getMessage());
        }
        if (!NAME_FORMAT.matcher(name).find()) {
            throw new InvalidValueException(ErrorMessage.INVALID_NAME.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
            && Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, time);
    }
}
