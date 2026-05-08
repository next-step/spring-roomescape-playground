package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.cglib.core.Local;
import roomescape.domain.Reservation;

public class ReservationResponse {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    public ReservationResponse(Long id, String name, LocalDate date, LocalTime time) {
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