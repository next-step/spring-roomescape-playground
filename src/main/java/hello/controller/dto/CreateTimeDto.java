package hello.controller.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalTime;

public class CreateTimeDto {

    @NotEmpty
    public String time;

    public CreateTimeDto() {}

    public LocalTime getTime() {
        return LocalTime.parse(time);
    }

}
