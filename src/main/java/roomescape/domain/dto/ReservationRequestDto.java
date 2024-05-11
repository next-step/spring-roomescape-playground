package roomescape.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ReservationRequestDto {

    public static class Create{

        @NotBlank
        private String name;

        @NotBlank
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
        private String date;

        private Long time;

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public Long getTimeId() {
            return time;
        }
    }

}
