package roomescape.domain.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.dto.ReservationDTO;
import roomescape.domain.time.Time;

public class Reservation {
    private long id;
    private final String name;
    private final LocalDate date;
    private final Time time;

    private final String INVALID_NULL_MSG = "입력받은 값이 없습니다..";
    private final String INVALID_NAME_FORMAT_MSG = "이름이 올바르지 않습니다.";

    public Reservation(String name, String date, Time time) {
        this.name = validateName(name);
        this.date = validateDate(date);
        this.time = time;
    }

    public ReservationDTO toReservationDTO() {
        return new ReservationDTO(
                this.id,
                this.name,
                this.date.toString(),
                this.time
        );
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

    public void setId(long id) {
        this.id = id;
    }

    public String validateName(String name) {
        validateIsNotNull(name);
        validateNameIsNotNumber(name);
        return name;
    }

    public LocalDate validateDate(String date) {
        validateIsNotNull(date);
        return LocalDate.parse(date);
    }

    public void validateIsNotNull(String value) {
        if(value.isEmpty()) {
            throw new IllegalArgumentException((INVALID_NULL_MSG));
        }
    }

    public void validateNameIsNotNumber(String name) {
        boolean isNumeric = name.chars().allMatch(Character::isDigit);

        if (isNumeric) {
            throw new IllegalArgumentException(INVALID_NAME_FORMAT_MSG);
        }
    }
}
