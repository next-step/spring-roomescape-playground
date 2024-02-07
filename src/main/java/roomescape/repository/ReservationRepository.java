package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.valid.ErrorCode;
import roomescape.valid.NotFoundReservationException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAllReservation() {
       return jdbcTemplate.query("select id, name, date, time from reservation",
                (resultSet, rowNum) -> {
                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                });
    }

    public Long save(Reservation reservation) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", reservation.getName());
        param.put("date", reservation.getDate());
        param.put("time", reservation.getTime());
        Number number = jdbcInsert.executeAndReturnKey(param);
        return number.longValue();
    }

    public void delete(Long id) {
        int count = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);

        if(count == 0) throw new NotFoundReservationException(ErrorCode.RESERVATION_NO_FOUND);
    }
}
