package roomescape.domain.reservation.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Reservation {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;

    public Reservation() {

    }

    public Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Long getId() { return id; }

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
