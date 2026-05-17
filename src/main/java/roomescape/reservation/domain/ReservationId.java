package roomescape.reservation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record ReservationId(@JsonValue long id) implements Comparable<ReservationId> {
    @JsonCreator
    public ReservationId {
    }

    @Override
    public int compareTo(ReservationId o) {
        return Long.compare(this.id, o.id);
    }
}
