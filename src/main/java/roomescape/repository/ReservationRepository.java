package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Times;
import roomescape.exception.NotFoundReservationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT " +
                "    r.id as reservation_id, " +
                "    r.name, " +
                "    r.date, " +
                "    t.id as time_id, " +
                "    t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("r.id"),
                            resultSet.getString("r.name"),
                            LocalDate.parse(resultSet.getString("r.date")),
                            new Times(LocalTime.parse(resultSet.getString("t.time")))
                    );
                    return reservation;
                });
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
                    PreparedStatement pstmt = conn.prepareStatement(
                            sql, new String[]{"id"});
                    pstmt.setString(1, reservation.getName());
                    pstmt.setString(2, reservation.getDate().toString());
                    pstmt.setLong(3, reservation.getTime().getId());
                    return pstmt;
                }, keyHolder);
        Long generatedKey = keyHolder.getKey().longValue();
        return new Reservation(generatedKey, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteById(int id){
        String sql = "delete from reservation where id = ?";
        int updatedRowCount = jdbcTemplate.update(sql, id);
        if(updatedRowCount == 0){
            throw new NotFoundReservationException("예약이 존재하지 않습니다.");
        }
    }
}
