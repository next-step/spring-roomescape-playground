package roomescape.domain;

import roomescape.global.exception.BadRequestException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static roomescape.global.exception.ExceptionMessage.INVALID_DATE;
import static roomescape.global.exception.ExceptionMessage.INVALID_NAME;
import static roomescape.global.exception.ExceptionMessage.INVALID_TIME;

public class Reservation {

    static final int MIN_NAME_LENGTH = 2;
    static final int MAX_NAME_LENGTH = 10;
    private static final Pattern NAME_FORMAT = Pattern.compile("^[가-힣]+$");

    private long id;

    private String name;

    private LocalDate date;

    private Time time;

    protected Reservation() {
    }

    public Reservation(final long id, final String name, final LocalDate date, final Time time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Reservation(final String name, final LocalDate date, final Time time) {
        validate(name, date, time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(final String name, final LocalDate date, final Time time) {
        validateName(name);
        validateDate(date);
        validateTime(time);
    }

    private void validateName(final String name) {
        validateNameExists(name);
        validateNameLength(name);
        validateNameFormat(name);
    }

    private void validateNameExists(final String name) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException(INVALID_NAME.getMessage());
        }
    }

    private void validateNameLength(final String name) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new BadRequestException(INVALID_NAME.getMessage());
        }
    }

    private void validateNameFormat(final String name) {
        if (!NAME_FORMAT.matcher(name).find()) {
            throw new BadRequestException(INVALID_NAME.getMessage());
        }
    }

    private void validateDate(final LocalDate date) {
        if (date == null) {
            throw new BadRequestException(INVALID_DATE.getMessage());
        }
    }

    private void validateTime(final Time time) {
        if (time == null) {
            throw new BadRequestException(INVALID_TIME.getMessage());
        }
    }

    public boolean isExpired(final Clock clock) {
        final LocalDateTime dateTime = date.atTime(time.getTime());
        return dateTime.isBefore(LocalDateTime.now(clock));
    }

    public long getId() {
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
