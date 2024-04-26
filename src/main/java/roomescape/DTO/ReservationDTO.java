package roomescape.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationDTO (
        @NotBlank  String name,
        @NotBlank String date,
        @NotNull Long time
){
    public ReservationDTO(String name, String date, Long time) {
      this.name = name;
      this.date = date;
      this.time = time;
    }
}

