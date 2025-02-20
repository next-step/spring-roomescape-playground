package roomescape.entity.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.entity.dto.ReservationCreateDto;
import roomescape.entity.dto.ReservationDto;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<ReservationDto> findAll() {
        String sql = """
            SELECT 
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.time AS time_value
            FROM reservation AS r
            INNER JOIN time AS t ON r.time_id = t.id
            """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new ReservationDto(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getLong("time_id")));
    }

    public Long save(ReservationCreateDto reservationCreateDto) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", reservationCreateDto.getName())
            .addValue("date", reservationCreateDto.getDate())
            .addValue("time_id", reservationCreateDto.getTimeId());
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
