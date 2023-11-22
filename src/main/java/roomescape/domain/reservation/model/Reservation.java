package roomescape.domain.reservation.model;

import static lombok.AccessLevel.PRIVATE;
import static roomescape.domain.reservation.exception.ReservationException.ErrorCode.INVALID_VALUE;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import roomescape.domain.reservation.exception.ReservationException;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class Reservation {

    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;

    @Builder
    private Reservation(Long id, String name, LocalDate date, LocalTime time) {
        this.id = id;
        validatedName(name);
        validatedDate(date);
        validatedTime(time);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validatedName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new ReservationException(INVALID_VALUE);
        }
    }

    private void validatedDate(LocalDate date) {
        if (date == null) {
            throw new ReservationException(INVALID_VALUE);
        }
    }

    private void validatedTime(LocalTime time) {
        if (time == null) {
            throw new ReservationException(INVALID_VALUE);
        }
    }

    public boolean isIdEquals(long reservationId) {
        return Objects.equals(this.id, reservationId);
    }

    public boolean isNameEquals(Reservation reservation) {
        return Objects.equals(this.name, reservation.getName());
    }
}
