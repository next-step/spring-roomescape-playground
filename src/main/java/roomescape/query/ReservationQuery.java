package roomescape.query;

public enum ReservationQuery {
    FIND_ALL("SELECT r.id as id, "
        + "r.name as name, "
        + "r.date as date, "
        + "t.id as time_id,"
        + "t.time as times "
        + "FROM reservation as r inner join time as t on r.time_id = t.id"),
    FIND_BY_ID("SELECT r.id as id, "
        + "r.name as name, "
        + "r.date as date, "
        + "t.id as time_id,"
        + "t.time as times "
        + "FROM reservation as r inner join time as t on r.time_id = t.id "
        + "WHERE r.id = ?"),
    DELETE("delete from reservation where id = ?");

    private final String query;

    ReservationQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
