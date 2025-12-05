package roomescape.dao.query;

public class SelectQuery {

    private final StringBuilder select = new StringBuilder("SELECT ");
    private String from = "";
    private String join = "";
    private String where = "";

    private SelectQuery(String column) {
        appendColumn(column);
    }

    public static SelectQuery select(String column) {
        return new SelectQuery(column);
    }

    public SelectQuery as(String alias) {
        select.append(" AS ").append(alias);
        return this;
    }

    public SelectQuery and(String column) {
        select.append(", ");
        appendColumn(column);
        return this;
    }

    public SelectQuery from(String fromClause) {
        this.from = " FROM " + fromClause;
        return this;
    }

    public SelectQuery innerJoin(String table, String onCondition) {
        this.join = " INNER JOIN " + table + " ON " + onCondition;
        return this;
    }

    public SelectQuery where(String condition) {
        this.where = " WHERE " + condition;
        return this;
    }

    public String build() {
        return select.toString() + from + join + where;
    }

    private void appendColumn(String column) {
        select.append(column);
    }
}
