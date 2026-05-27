package roomescape.reservation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record ReservationId(@JsonValue long id) {
    @JsonCreator
    public ReservationId {
    }
}
