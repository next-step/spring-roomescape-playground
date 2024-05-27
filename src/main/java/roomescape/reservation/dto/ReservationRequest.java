package roomescape.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String date;

    @NotBlank
    private Long timeId;
}
