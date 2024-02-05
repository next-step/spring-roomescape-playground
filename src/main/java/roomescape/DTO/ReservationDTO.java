package roomescape.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor @Getter
public class ReservationDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
}
