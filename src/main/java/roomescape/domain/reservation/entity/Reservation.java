package roomescape.domain.reservation.entity;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import roomescape.domain.time.entity.Time;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class Reservation {

    private Long id;
    private String name;
    private LocalDate date;
    private Time time;

    @Builder
    private Reservation(Long id, String name, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isIdEquals(long reservationId) {
        return Objects.equals(this.id, reservationId);
    }
}
