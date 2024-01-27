package hello.repository;

import hello.controller.dto.CreateReservationDto;
import hello.domain.Reservation;
import hello.exceptions.NotFoundReservationException;
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

    public ReservationRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rn) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            rs.getTime("time").toLocalTime()
    );

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation";
        return template.query(sql, reservationRowMapper);
    }

    public Reservation save(CreateReservationDto dto) {

        Map<String, Object> params = new HashMap<>();
        params.put("name", dto.getName());
        params.put("date", dto.getDate());
        params.put("time", dto.getTime());

        Number key = jdbcInsert.executeAndReturnKey(params);
        long savedId = Objects.requireNonNull(key).longValue();

        return new Reservation(savedId, dto.getName(), dto.getDate(), dto.getTime());
    }

    public void delete(Long id) {
        String sql = "delete from reservation where id = ?";
        int count = template.update(sql, id);

        if(count==0) throw new NotFoundReservationException();
    }

}
