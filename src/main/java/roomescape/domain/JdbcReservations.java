package roomescape.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReservations {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation add(Reservation reservation) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservation);
        Long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return reservation.with(id);
    }

    public List<Reservation> add(Reservation... reservations) {
        List<Reservation> saved = new ArrayList<>();
        for (Reservation reservation : reservations) {
            saved.add(add(reservation));
        }
        return saved;
    }

    public List<Reservation> getAll() {
        return jdbcTemplate.queryForStream(
                "SELECT id, name, date, time FROM reservation",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("time")
                )
        ).toList();
    }

    public void cancel(Long id) {
        int affectedCount = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (affectedCount == 0) {
            throw new IllegalArgumentException("id가 " + id + "인 예약을 찾을 수 없습니다");
        }
    }
}
