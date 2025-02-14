package roomescape.entity;

import roomescape.global.exception.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import static roomescape.global.exception.ExceptionMessage.INVALID_DATE;
import static roomescape.global.exception.ExceptionMessage.INVALID_NAME;
import static roomescape.global.exception.ExceptionMessage.INVALID_TIME;

public class Reservation {

    public static final AtomicLong RESERVATION_ID = new AtomicLong(0);
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

    public Reservation(String name, LocalDate date, LocalTime time) {
        validate(name, date, time);
        this.id = RESERVATION_ID.incrementAndGet();
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isSameId(final long reservationId) {
        return id.equals(reservationId);
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
