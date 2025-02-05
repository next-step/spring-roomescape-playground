package roomescape.domain.reservation;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ReserveDate {

    private final LocalDate value;

    public ReserveDate(LocalDate reserveDate) {
        valid(reserveDate);
        value = reserveDate;
    }

    private void valid(LocalDate reserveDate) {
        if (Objects.isNull(reserveDate)) {
            throw new IllegalArgumentException();
        }
        if (reserveDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException();
        }
    }
}
