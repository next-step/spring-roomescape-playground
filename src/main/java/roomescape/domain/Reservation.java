package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Reservation {

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;
}
