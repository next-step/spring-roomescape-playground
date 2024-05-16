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
        validateRequestField(date, name, time);
        this.date = date;
        this.name = name;
        this.time = time;
    }

    private void validateRequestField(final LocalDate date, final String name, final LocalTime time) {
        if (date == null) {
            throw new ReservationException(ErrorMessage.EMPTY_DATE);
        }
        if (name.isEmpty()) {
            throw new ReservationException(ErrorMessage.EMPTY_NAME);
        }
        if (time == null) {
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
