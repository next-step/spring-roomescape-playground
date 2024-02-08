package hello.repository;

import hello.domain.Reservation;
import hello.domain.Time;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ReservationRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;
    private final TimeRepository timeRepository;

    public ReservationRepository(DataSource dataSource, TimeRepository timeRepository) {
        this.template = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.timeRepository = timeRepository;
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rn) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new Time(
                    rs.getLong("time_id"),
                    rs.getTime("time_value").toLocalTime()
            )
    );

    public List<Reservation> findAllReservations() {

        String sql = "select r.id as reservation_id, " +
                "r.name, " +
                "r.date, " +
                "t.id as time_id, " +
                "t.time as time_value " +
                "from reservation as r join time as t on r.time_id = t.id";

        return template.query(sql, reservationRowMapper);
    }

    public Reservation save(String name, LocalDate date, Long timeId) {

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("date", date);
        params.put("time_id", timeId);

        Number key = jdbcInsert.executeAndReturnKey(params);
        long savedId = Objects.requireNonNull(key).longValue();

        return new Reservation(savedId, name, date, timeRepository.findById(timeId));

    }

    public int delete(Long id) {
        String sql = "delete from reservation where id = ?";
        return template.update(sql, id);
    }
}