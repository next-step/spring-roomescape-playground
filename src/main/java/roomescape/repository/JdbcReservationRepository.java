package roomescape.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationResponse;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    public JdbcReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> Reservation.of(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
    );

    @Override
    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = jdbcTemplate.query("SELECT * FROM reservation ORDER BY id", rowMapper);
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    @Override
    public ReservationResponse save(Reservation reservation) {
        Number key = insert.executeAndReturnKey(Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time", reservation.getTime()
        ));
        Reservation saved = Reservation.of(
                key.longValue(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
        return new ReservationResponse(saved);
    }


    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
