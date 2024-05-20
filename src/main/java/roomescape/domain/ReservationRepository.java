package roomescape.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findReservation() {

        return jdbcTemplate.query(
                "select * from reservation",
                (resultSet, rowNum) ->
                        new Reservation(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("date"),
                                resultSet.getString("time")
                        )
        );
    }
}