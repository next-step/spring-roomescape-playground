package roomescape.domain.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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

    public String getDateToString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(dateFormatter);
    }

    public String getTimeToString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(timeFormatter);
    }
}
