package roomescape.query;

public enum TimeQuery {

    FIND_BY_ID("SELECT id, time FROM time WHERE id = ?");

    private final String query;

    TimeQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
