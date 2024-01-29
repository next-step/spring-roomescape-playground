package hello.controller.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String date;
    @NotEmpty
    private String time;

    public CreateReservationDto() {}

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public LocalTime getTime() {
        return LocalTime.parse(time);
    }
}
