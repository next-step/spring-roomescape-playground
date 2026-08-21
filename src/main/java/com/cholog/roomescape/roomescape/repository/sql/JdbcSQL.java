package com.cholog.roomescape.roomescape.repository.sql;

public enum JdbcSQL {

    FIND_BY_ID("""
            select id, name, date, time from reservation where id = ?
            """),
    FIND_ALL("""
            select id, name, date, time from reservation
    """),
    SAVE("""
            insert into reservation(name, date, time) values (?, ?, ?)
            """),
    DELETE("""
            delete from reservation where id = ?
            """)
    ;

    JdbcSQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    private final String sql;
}
