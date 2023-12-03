package roomescape.domain.reservation.entity;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;

    @Builder
    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isIdEquals(long reservationId) {
        return Objects.equals(this.id, reservationId);
    }
}
