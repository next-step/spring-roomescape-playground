package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
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

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
        Long reservationId = rs.getLong("reservation_id");
        String name = rs.getString("name");
        LocalDate date = LocalDate.parse(rs.getString("date"));

        Long timeId = rs.getLong("time_id");
        String timeValue = rs.getString("time_value");
        Time time = Time.of(timeId, timeValue);

        return Reservation.of(reservationId, name, date, time);
    };

    @Override
    public List<ReservationResponse> findAll() {
        String sql = """
                SELECT 
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.time AS time_value
                FROM reservation r
                INNER JOIN time t ON r.time_id = t.id
                ORDER BY r.id
                """;

        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }


    @Override
    public ReservationResponse save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        Reservation saved = Reservation.of(
                generatedId,
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
