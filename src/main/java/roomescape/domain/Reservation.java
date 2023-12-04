package roomescape.domain;

import static roomescape.utils.ErrorMessage.EMPTY_DATE_ERROR;
import static roomescape.utils.ErrorMessage.EMPTY_NAME_ERROR;
import static roomescape.utils.ErrorMessage.EMPTY_TIME_ERROR;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;

@Getter
public final class Reservation {
    private final Long id;
    @NotEmpty(message = EMPTY_NAME_ERROR)
    private final String name;
    @NotEmpty(message = EMPTY_DATE_ERROR)
    private final String date;
    @NotNull(message = EMPTY_TIME_ERROR)
    private final Long time;

    public Reservation(Long id, String name, String date, Long time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation of(Long id, String name, String date, Long time) {
        return new Reservation(id, name, date, time);
    }

    public static Reservation createWithId(Reservation reservation, long id) {
        return new Reservation(id, reservation.name, reservation.date, reservation.time);
    }


}
