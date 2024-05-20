package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequest;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;
import roomescape.model.Reservation;

@Repository
public class H2ReservationRepository implements ReservationRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservations")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "select * from reservations";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getObject("date", LocalDate.class),
                        rs.getObject("time", LocalTime.class)
                )
        );
    }

    @Override
    public Reservation save(final ReservationRequest request) {
//        String sql = "insert into reservations (name, date, time) "
//                + "values (:name, :date, :time)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(request);
        final long savedId = jdbcInsert.executeAndReturnKey(param).longValue();
        return new Reservation(savedId, request.name(), request.date(), request.time());
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(sql, param, keyHolder);
//        final long keyId = keyHolder.getKey().longValue();
//        return new Reservation(keyId, reservation.getName(), reservation.getDate(),
//                reservation.getTime());
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "delete from reservations where id = :id";
        Map<String, Object> param = Map.of("id", id);
        final int rowNum = jdbcTemplate.update(sql, param);
        if (rowNum == 0) {
            throw new ReservationException(ErrorMessage.INVALID_ID_REQUEST);
        }
    }

}
