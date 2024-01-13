package roomescape.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReadReservationResponse {

    private final Long id;

    private final String name;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private final LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private final LocalTime time;

    private ReadReservationResponse(final Long id, final String name, final LocalDate date, final LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReadReservationResponse from(final Reservation reservation) {
        return new ReadReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
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
