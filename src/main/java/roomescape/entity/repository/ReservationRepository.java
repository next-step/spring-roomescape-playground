package roomescape.entity.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.Dto.ReservationInDto;
import roomescape.entity.Dto.ReservationOutDto;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<ReservationOutDto> findAll() {
        String sql = "SELECT \n" +
            "    r.id as reservation_id, \n" +
            "    r.name, \n" +
            "    r.date, \n" +
            "    t.id as time_id, \n" +
            "    t.time as time_value \n" +
            "FROM reservation as r inner join time as t on r.time_id = t.id\n";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new ReservationOutDto(
                rs.getLong("reservation_id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getLong("time_id")));
    }

    public Long save(ReservationInDto reservationInDto) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", reservationInDto.getName())
            .addValue("date", reservationInDto.getDate())
            .addValue("time_id", reservationInDto.getTimeId());
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
