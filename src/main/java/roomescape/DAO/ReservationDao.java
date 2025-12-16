package roomescape.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    public ReservationDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) ->
            new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    new Time(resultSet.getLong("time_id"),resultSet.getString("time"))
            );

    // Read
    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id AS id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.time AS time
                FROM reservation r
                INNER JOIN time t ON r.time_id = t.id
            """;
        return jdbcTemplate.query(sql,rowMapper);
    }

    // Create
    public Long add(Reservation reservation) {

        String sql = "insert into reservation (name,date,time_id) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to retrieve generated id");
        }
        return key.longValue();
    }

    // Delete
    public int deleteByid(Long id){
        String sql = "delete from reservation where id = ?";
        int deleted = jdbcTemplate.update(sql,id);
        return deleted;
    }
}
