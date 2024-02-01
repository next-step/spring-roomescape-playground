package hello.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class CreateReservationDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String date;

    @NotEmpty
    @JsonProperty("time")
    private String time_id;

    public CreateReservationDto() {}

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public String getTime_id() {
        return time_id;
    }
}
