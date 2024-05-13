package roomescape.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservationRequestDto {
    private String name;
    private LocalDate date;
    private LocalTime time;
}
