package roomescape.DTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Setter(AccessLevel.NONE)
@Builder
public class ReservationDTO {
    private Long id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be past or present")
    private LocalDate date;

    @NotNull(message = "Time cannot be null")
    private LocalTime time;
}
