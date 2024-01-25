package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import roomescape.domain.dto.ReservationAddRequest;

@Repository
public class ReservationUpdatingDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationUpdatingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createReservation(ReservationAddRequest reservationAddRequest) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingColumns("name", "date", "time_id")
            .usingGeneratedKeyColumns("id");

        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("name", reservationAddRequest.getName())
            .addValue("date", reservationAddRequest.getDate())
            .addValue("time_id", Long.valueOf(reservationAddRequest.getTime()));
        Number key = simpleJdbcInsert.executeAndReturnKey(parameterSource);

        return key.longValue();
    }

    public int deleteReservation(Long id) {
        String sql = "DELETE FROM reservation where id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
