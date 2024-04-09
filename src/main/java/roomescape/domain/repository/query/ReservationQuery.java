package roomescape.domain.repository.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationQuery {
    FIND_ALL("SELECT r.id, r.name, r.date, t.id as time_id, t.time FROM reservation r, time t WHERE r.time_id = t.id"),
    EXIST_BY_ID("SELECT COUNT(*) FROM reservation WHERE id = ?"),
    DELETE("DELETE FROM reservation WHERE id = ?");

    private final String query;
}
