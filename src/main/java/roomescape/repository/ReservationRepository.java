package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Reservation> findAll(){
        List<Reservation> reservations = jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) -> {
                    Reservation reservation = Reservation.builder().
                            id(resultSet.getLong("id")).
                            name(resultSet.getString("name")).
                            date(resultSet.getString("date")).
                            time(resultSet.getString("time")).
                            build();

                    return reservation;
                });
        return reservations;
    }

    public Long createReservation(Reservation reservation){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)", new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int deleteReservation(Long id){
        return jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
