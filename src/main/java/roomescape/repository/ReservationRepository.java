package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO DB에서 reservation LIST 찾아서 반환
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "select * from reservation",
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date")),
                            LocalTime.parse(resultSet.getString("time"))
                    );
                    return reservation;
                });
    }

    public int save(Reservation reservation) {
        return jdbcTemplate.update(
                "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)",
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public int deleteById(int id){
        return jdbcTemplate.update(
                "delete from reservation where id = ?",
                id
        );
    }
}
