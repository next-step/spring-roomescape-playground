package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Time;
import roomescape.domain.Reservation;

public class ReservationResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    public ReservationResponse(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTimeInfo()
        );
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public Time getTime() { return time; }
}
