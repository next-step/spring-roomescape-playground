package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    private final String INVALID_DATE_FORMAT_MSG = "올바르지 않은 날짜 형식입니다.";
    private final String INVALID_TIME_FORMAT_MSG = "올바르지 않은 시간 형식입니다.";
    private final String INVALID_NAME_NULL_MSG = "이름을 입력해주세요.";
    private final String INVALID_NAME_FORMAT_MSG = "이름이 올바르지 않습니다.";

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = validateName(name);
        this.date = validateDateIsNotNull(date);
        this.time = validateTimeIsNotNull(time);
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

    public static Reservation toEntity(Reservation reservation, Long id) {
        return new Reservation(id,
                reservation.name,
                reservation.date,
                reservation.time);
    }

    public String validateName(String name) {
        validateNameIsNotNull(name);
        validateNameIsNotNumber(name);
        return name;
    }

    public void validateNameIsNotNumber(String name) {
        boolean isNumeric = name.chars().allMatch(Character::isDigit);

        if (isNumeric) {
            throw new IllegalArgumentException(INVALID_NAME_FORMAT_MSG);
        }
    }

    public void validateNameIsNotNull(String name) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException((INVALID_NAME_NULL_MSG));
        }
    }

    public LocalDate validateDateIsNotNull(LocalDate date) {
        if(Objects.isNull(date)) {
            throw new IllegalArgumentException(INVALID_DATE_FORMAT_MSG);
        }
        return date;
    }

    public LocalTime validateTimeIsNotNull(LocalTime time) {
        if(Objects.isNull(time)){
            throw new IllegalArgumentException(INVALID_TIME_FORMAT_MSG);
        }
        return time;
    }
}
