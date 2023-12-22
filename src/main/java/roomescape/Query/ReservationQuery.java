package roomescape.Query;

public enum ReservationQuery {
    FIND_ALL("SELECT id, name, date, time FROM reservation"),
    FIND_BY_ID("SELECT id, name, date, time FROM reservation WHERE id = ?"),
    DELETE("delete from reservation where id = ?");

    private final String query;

    ReservationQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
