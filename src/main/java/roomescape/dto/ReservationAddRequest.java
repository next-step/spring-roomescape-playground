package roomescape.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import roomescape.exception.InvalidReservationException;

public class ReservationAddRequest {
    private String name;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate date;
    private Long time;

    public ReservationAddRequest(String name, LocalDate date, Long time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public ReservationAddRequest() {
        super();
    }

    private void isValidReservation() {
        if (this.name.isEmpty() || this.name.isBlank())
            throw new InvalidReservationException();
        if (this.date == null)
            throw new InvalidReservationException();
        if (this.time == null)
            throw new InvalidReservationException();
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getTime() {
        return time;
    }
}
