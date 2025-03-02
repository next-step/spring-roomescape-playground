package roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public record CreateReservationRequest(
        String name,

        LocalDate date,

        @JsonProperty("timeId")
        long timeId
) {
    public Reservation toReservation(final Time time) {
        return new Reservation(
                name,
                date,
                time
        );
    }
}
