package roomescape.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationResponse {
    private final Long id;
    private final String name;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private final LocalDate date;
    private final TimeResponse time;

    public ReservationResponse(final Long id, final String name, final LocalDate date, final TimeResponse time) {
        this.id = id;
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

    public TimeResponse getTime() {
        return time;
    }

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), TimeResponse.from(reservation.getTime()));
    }

    public static List<ReservationResponse> from(final List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), TimeResponse.from(reservation.getTime())))
                .collect(Collectors.toList());
    }
}
