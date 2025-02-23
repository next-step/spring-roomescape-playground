package roomescape.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.exception.DataInvalidException;


@Repository
public class ReservationJdbcDAO implements ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper = new ReservationRowMapper();
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationJdbcDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public Reservation create(Reservation reservation) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time", reservation.getTime());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new Reservation(generatedId.longValue(), reservation.getName(), reservation.getDate(),
                reservation.getTime());
    }

    @Override
    public List<Reservation> getAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }


    @Override
    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new DataInvalidException("예약을 찾을 수 없습니다. ID: " + id);
        }
    }


    @Override
    public Reservation getById(long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataInvalidException("해당 ID의 예약을 찾을 수 없습니다 : " + e.getMessage());
        }
    }

}
