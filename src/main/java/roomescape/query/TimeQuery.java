package roomescape.query;

public enum TimeQuery {
    FIND_ALL("SELECT id, time FROM time"),
    FIND_BY_ID("SELECT id, time FROM time WHERE id = ?"),
    FIND_BY_TIME("SELECT id, time FROM time WHERE time = ?"),
    DELETE("delete from time where id = ?");

    private final String query;

    TimeQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
