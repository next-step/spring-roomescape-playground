package roomescape.domain.repository.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationQuery {
    FIND_ALL("SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
            "FROM reservation as r inner join time as t on r.time_id = t.id"),
    EXIST_BY_ID("SELECT COUNT(*) FROM reservation WHERE id = ?"),
    DELETE("DELETE FROM reservation WHERE id = ?");

    private final String query;
}