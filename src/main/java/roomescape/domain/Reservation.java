package roomescape.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;
}
