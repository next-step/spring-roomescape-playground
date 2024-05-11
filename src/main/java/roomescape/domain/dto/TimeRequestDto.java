package roomescape.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TimeRequestDto {

    public static class Create{

        @NotBlank
        @Pattern(regexp = "\\d{2}:\\d{2}")
        private String time;

        public String getTime() {
            return time;
        }
    }
}
