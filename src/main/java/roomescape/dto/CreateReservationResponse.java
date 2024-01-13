package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationResponse {

    private final Long id;

    private final String name;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    private CreateReservationResponse(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static CreateReservationResponse from(final Reservation newReservation) {
        return new CreateReservationResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
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

    public LocalTime getTime() {
        return time;
    }
}
