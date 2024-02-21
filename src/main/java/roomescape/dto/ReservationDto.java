package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ReservationDto {
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String date;
    @NotBlank
    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String time;
}