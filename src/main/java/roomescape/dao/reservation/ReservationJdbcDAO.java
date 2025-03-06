package roomescape.dao.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidException;


@Repository
public class ReservationJdbcDAO implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public ReservationJdbcDAO(JdbcTemplate jdbcTemplate, RowMapper<Reservation> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = rowMapper;
    }

    @Override
    public Reservation create(Reservation reservation) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("id", reservation.getTime().getId());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Reservation(generatedId.longValue(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    @Override
    public List<Reservation> getAll() {
        String sql = """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.time as time_value 
                 FROM reservation as r
                    INNER JOIN time as t ON r.time_id = t.id
                """;
        return jdbcTemplate.query(sql, rowMapper);

    }


    @Override
    public Reservation getById(long id) {
        String sql = """
                    SELECT 
                        r.id as reservation_id, 
                        r.name, 
                        r.date, 
                        t.id as time_id, 
                        t.time as time_value 
                    FROM reservation as r 
                    INNER JOIN time as t ON r.time_id = t.id
                    WHERE r.id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidException("예약을 찾을 수 없습니다. ID : " + id);
        }
    }


    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }


}
