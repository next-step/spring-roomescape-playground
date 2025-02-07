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
    private LocalDate reservationDate;
    private LocalTime reservationTime;

    public Reservation(Long id, String name, LocalDate reservationDate, LocalTime reservationTime) {
        this.id = id;
        validate(name, reservationDate, reservationTime);
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    private void validate(String name, LocalDate reservationDate, LocalTime reservationTime) {
        validateName(name);
        validateDate(reservationDate);
        validateTime(reservationTime);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
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
