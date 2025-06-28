package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.exception.BadRequestException;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReservationDao implements ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = """
        SELECT 
            r.id as reservation_id, 
            r.name, 
            r.date, 
            t.id as time_id, 
            t.time as time_value 
        FROM reservation as r 
        INNER JOIN time as t ON r.time_id = t.id
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("date")),
                        new Time(
                                rs.getLong("time_id"),
                                LocalTime.parse(rs.getString("time_value"))
                        )
                )
        );
    }

    @Override
    public Reservation save(Reservation reservation) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", reservation.name());
        params.put("date", reservation.date().toString());
        params.put("time_id", reservation.time().id());

        Number id = jdbcInsert.executeAndReturnKey(params);
        return new Reservation(id.longValue(), reservation.name(), reservation.date(), reservation.time());
    }

    @Override
    public void deleteById(Long id) {
        int result = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (result == 0) {
            throw new BadRequestException("삭제할 예약이 존재하지 않습니다.");
        }
    }
}
