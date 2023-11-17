package roomescape.domain.reservation.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
}
