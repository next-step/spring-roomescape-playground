package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import roomescape.domain.Reservation;

public class ReservationResponse {
    private final Long id;
    private final String name;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private final LocalDate date;
    private final LocalTime time;

    public ReservationResponse(final Reservation reservation) {
        this.id = reservation.getId();
        this.name = reservation.getName();
        this.date = reservation.getDate();
        this.time = reservation.getTime().getTime();
    }
}
