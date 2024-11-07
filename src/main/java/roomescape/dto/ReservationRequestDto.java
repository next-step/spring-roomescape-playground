package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReservationRequestDto {

  @NotBlank
  private String name;

  @NotBlank
  private String date;

  @NotBlank
  private String time;
}
