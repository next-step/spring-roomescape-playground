package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;

public class ReservationRequestDto {

    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public ReservationRequestDto(final LocalDate date, final String name, final LocalTime time) {
        validateRequestField();
        this.date = date;
        this.name = name;
        this.time = time;
    }

    private void validateRequestField() {
        if (this.date == null) {
            throw new ReservationException(ErrorMessage.EMPTY_DATE);
        }
        if (this.name.isEmpty()) {
            throw new ReservationException(ErrorMessage.EMPTY_NAME);
        }
        if (this.time == null) {
            throw new ReservationException(ErrorMessage.EMPTY_TIME);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public LocalTime getTime() {
        return time;
    }

}
