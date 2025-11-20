package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation",
                rowMapper
        );
    }

    public long insert(String name, String date, String time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, time);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}


