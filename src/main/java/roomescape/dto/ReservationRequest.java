package roomescape.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationRequest {
    private final String name;
    private final LocalDate date;
    private final Long time;
}
