package roomescape.domain.repository.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationQuery {
    FIND_ALL("SELECT id, name, date, time FROM reservation"),
    EXIST_BY_ID("SELECT COUNT(*) FROM reservation WHERE id = ?"),
    DELETE("DELETE FROM reservation WHERE id = ?");

    private final String query;
}