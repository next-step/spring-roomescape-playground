package hello.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationDto {

    @NotEmpty
    private String name;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;

    public CreateReservationDto() {}

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
