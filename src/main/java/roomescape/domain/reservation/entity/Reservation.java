package roomescape.domain.reservation.entity;

import lombok.*;
import roomescape.domain.time.entity.Time;

import java.time.LocalDate;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {
    private Long id;
    private String name;
    private LocalDate date;
    private Time time;
}
