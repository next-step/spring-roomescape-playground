package roomescape.repository;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.reservation.ReservationRequest;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime()
            );
            return reservation;
        };
    }

    public List<Reservation> findAllReservation() {
        return jdbcTemplate.query("select * from reservation", reservationRowMapper());
    }

    public Long saveReservation(ReservationRequest reservationRequest) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("reservation").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = Map.of(
                "name", reservationRequest.getName(),
                "date", reservationRequest.getDate(),
                "time", reservationRequest.getTime()
        );

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.longValue();
    }

    public Reservation findReservationById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper(), id);
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
