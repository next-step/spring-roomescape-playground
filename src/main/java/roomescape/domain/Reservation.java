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
        validateName(name);
        this.name = name;
        this.date = date;
        this.time = time;
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
}
