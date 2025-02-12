package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;

public class Reservation {
    private static final Pattern NAME_FORMAT = Pattern.compile("^[가-힣]+$");

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate reservationDate, LocalTime reservationTime) {
        validateName(name);
        validateDate(reservationDate);
        validateTime(reservationTime);
    }

    public boolean isSameReservation(Long reservationId) {
        if (this.id.equals(reservationId)) {
            return true;
        }
        return false;
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

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidValueException(ErrorMessage.INVALID_NAME.getMessage());
        }
        if (!NAME_FORMAT.matcher(name).find()) {
            throw new InvalidValueException(ErrorMessage.INVALID_NAME.getMessage());
        }
    }

    private void validateDate(LocalDate reservationDate) {
        if (reservationDate == null) {
            throw new InvalidValueException(ErrorMessage.INVALID_DATE.getMessage());
        }
    }

    private void validateTime(LocalTime reservationTime) {
        if (reservationTime == null) {
            throw new InvalidValueException(ErrorMessage.INVALID_TIME.getMessage());
        }
    }
}
