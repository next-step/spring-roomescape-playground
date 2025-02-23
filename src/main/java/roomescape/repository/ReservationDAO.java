package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.mapper.ReservationRowMapper;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationRowMapper reservationRowMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDAO(JdbcTemplate jdbcTemplate, ReservationRowMapper reservationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRowMapper = reservationRowMapper;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation")
            .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findReservations() {
        String sql = "select id, name, reservation_date, reservation_time from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public ReservationResponse createReservation(ReservationCreateRequest request) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", request.name());
        parameters.put("reservation_date", Date.valueOf(request.date()));
        parameters.put("reservation_time", Time.valueOf(request.time()));

        long key = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return new ReservationResponse(
            key,
            request.name(),
            request.date(),
            request.time()
        );
    }

    public void deleteReservation(long reservationId) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, reservationId);
    }
}
