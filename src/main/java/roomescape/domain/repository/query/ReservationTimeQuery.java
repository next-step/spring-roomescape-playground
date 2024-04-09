package roomescape.domain.repository.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ReservationTimeQuery {
    FIND_ALL("SELECT id, time FROM time"),
    EXIST_BY_ID("SELECT COUNT(*) FROM time WHERE id = ?"),
    DELETE("DELETE FROM time WHERE id = ?"),
    FIND_BY_ID("SELECT * FROM time WHERE id = ?");
    private final String query;
}
