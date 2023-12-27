package roomescape.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.repository.rowMapper.ReservationRowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class JdbcReservationRepository {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    public JdbcReservationRepository(JdbcTemplate template, DataSource source) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(source)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations;
        try {
            reservations = template.query("""
                            SELECT
                            r.id as reservation_id
                            , r.name
                            , r.date
                            , t.id as time_id
                            , t.time as time_value
                            FROM reservation as r
                            inner join time as t
                            on r.time_id = t.id
                            """,
                    new ReservationRowMapper());
        } catch (DataAccessException e) {
            throw new NoSuchElementException(e);
        }

        return reservations;
    }


    public Long save(Reservation newReservation) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", newReservation.getName());
        params.addValue("date", newReservation.getDate());
        params.addValue("time_id", newReservation.getTime().getId());

        return insert.executeAndReturnKey(params).longValue();
    }


    public Reservation findById(Long id) {
        Reservation reservation;
        try {
            reservation = template.queryForObject("""
                            SELECT
                            r.id as reservation_id
                            , r.name
                            , r.date
                            , t.id as time_id
                            , t.time as time_value
                            FROM reservation as r
                            inner join time as t
                            on r.time_id = ?
                            """,
                    new ReservationRowMapper(),
                    id);
        } catch (DataAccessException e) {
            throw new NoSuchElementException(e);
        }

        return reservation;
    }

    public void delete(Long id) {
        findById(id);
        template.update("delete from reservation where id = ?", id);
    }
}
