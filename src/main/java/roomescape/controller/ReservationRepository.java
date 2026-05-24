package roomescape.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {

        String sql = """
                SELECT id, name, date, time 
                FROM reservation
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("time")
                )
        );
    }

    public void insert(Reservation reservation) {
        String sql = """
                INSERT INTO reservation (id, name, date, time)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public void delete(Long id) {
        String sql = """
                DELETE FROM reservation WHERE id = 1
                """;
        jdbcTemplate.update(sql, id);
    }
}
