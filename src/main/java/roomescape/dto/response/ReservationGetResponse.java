package roomescape.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationGetResponse {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
}
