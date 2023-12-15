package roomescape.Repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.util.List;

import static roomescape.query.ReservationQuery.FIND_ALL;
import static roomescape.query.ReservationQuery.FIND_BY_ID;

@Repository
public class ReservationRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    public ReservationRepository(JdbcTemplate template, DataSource source) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(source)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        return template.query(FIND_ALL.getQuery(),
                (rs, rowNum) -> new Reservation(rs.getLong("id"),
                        rs.getString("name"), rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime()));
    }


    public Long save(Reservation newReservation) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(newReservation);

        return insert.executeAndReturnKey(params).longValue();
    }


    public Reservation findById(Long id) {
        return template.queryForObject(FIND_BY_ID.getQuery(),
                ((rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"),
                        rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime())), id);
    }

    public void cancel(Long id) {
        template.update("delete from reservation where id = ?", id);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM reservations WHERE id = ?";
        int count = template.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}
