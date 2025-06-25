package roomescape.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = jdbcTemplate.query(
                "SELECT * FROM reservation",
                (rs, rowNum) -> Reservation.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                )
        );

        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.getName());
        params.put("date", request.getDate());
        params.put("time", request.getTime());

        Number generatedId = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        Reservation saved = Reservation.of(generatedId.longValue(), request.getName(), request.getDate(),
                request.getTime());

        return new ReservationResponse(saved);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}

