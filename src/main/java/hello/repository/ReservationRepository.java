package hello.repository;

import hello.controller.dto.CreateReservationDto;
import hello.domain.Reservation;
import hello.domain.Time;
import hello.exceptions.NotFoundReservationException;
import hello.exceptions.NotSelectTimeException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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

    public Reservation save(CreateReservationDto dto) {

        String dtoTime_id = dto.getTime_id();
        try {
            Long timeId = Long.parseLong(dtoTime_id);
            Map<String, Object> params = new HashMap<>();
            params.put("name", dto.getName());
            params.put("date", dto.getDate());
            params.put("time_id", dto.getTime_id());

            Number key = jdbcInsert.executeAndReturnKey(params);
            long savedId = Objects.requireNonNull(key).longValue();

            return new Reservation(savedId, dto.getName(), dto.getDate(), timeRepository.findById(timeId));
        } catch (NumberFormatException e) {
            throw new NotSelectTimeException();
        }
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        int count = template.update(sql, id);

        if (count == 0) throw new NotFoundReservationException();
    }
}
