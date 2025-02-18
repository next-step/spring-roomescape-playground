package roomescape.domain;

import roomescape.global.exception.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

import static roomescape.global.exception.ExceptionMessage.INVALID_DATE;
import static roomescape.global.exception.ExceptionMessage.INVALID_NAME;
import static roomescape.global.exception.ExceptionMessage.INVALID_TIME;

public class Reservation {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 10;
    private static final Pattern NAME_FORMAT = Pattern.compile("^[가-힣]+$");

    private Long id;

    private String name;

    private LocalDate date;

    private LocalTime time;

    protected Reservation() {
    }

    public Reservation(final Long id, final String name, final LocalDate date, final LocalTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final LocalTime time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
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

    private void validate(final String name, final LocalDate date, final LocalTime time) {
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    private void validateName(final String customerName) {
        validateNameExists(customerName);
        validateNameLength(customerName);
        validateNameFormat(customerName);
    }

    private void validateNameExists(final String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new BadRequestException(INVALID_NAME.getMessage());
        }
    }

    private void validateNameLength(final String customerName) {
        if (customerName.length() < MIN_NAME_LENGTH || customerName.length() > MAX_NAME_LENGTH) {
            throw new BadRequestException(INVALID_NAME.getMessage());
        }
    }

    private void validateNameFormat(final String customerName) {
        if (!NAME_FORMAT.matcher(customerName).find()) {
            throw new BadRequestException(INVALID_NAME.getMessage());
        }
    }

    private void validateTime(final LocalTime time) {
        if (time == null) {
            throw new BadRequestException(INVALID_TIME.getMessage());
        }
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new BadRequestException(INVALID_DATE.getMessage());
        }
    }
}
