package roomescape.domain.reservation.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {

    public static AtomicLong pk = new AtomicLong(1);

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
}
