package roomescape.query;

public enum ReservationQuery {
    SELECT_RESERVATIONS("SELECT id, name, date, time FROM reservation");

    private final String query;

    ReservationQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
