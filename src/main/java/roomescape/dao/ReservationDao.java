package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation add(Reservation reservation) {
        SqlParameterSource parameterSource = new MapSqlParameterSource(Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getTime().getId()
        ));
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
                """
                        SELECT reservation.id, name, date, time.id, time
                        FROM reservation left join time 
                        on time_id = time.id""",
                (rs, rowNum) -> new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        TimeDao.TIME_ROW_MAPPER.mapRow(rs, rowNum)
                )
        ).toList();
    }

    public void deleteBy(Long id) {
        int affectedCount = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        if (affectedCount == 0) {
            throw new IllegalArgumentException("id가 " + id + "인 예약을 찾을 수 없습니다");
        }
    }
}
