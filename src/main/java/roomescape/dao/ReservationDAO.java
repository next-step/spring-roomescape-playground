package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;
import roomescape.dto.Time;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ReservationDAO {
    private JdbcTemplate jdbcTemplate;

    public ReservationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.time as time_value \n" +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("reservation_id"),
                            resultSet.getString("name"),
                            LocalDate.parse(resultSet.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            Time.toEntity(resultSet.getString("time_value"), resultSet.getLong("time_id"))
                    );
                    return reservation;
                });
    }

    public Long insertWithKeyHolder(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().getTime());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return id;
    }

    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }

}