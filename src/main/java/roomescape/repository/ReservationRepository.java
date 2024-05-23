package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
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

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {
                    PreparedStatement pstmt = conn.prepareStatement(
                            sql, new String[]{"id"});
                    pstmt.setString(1, reservation.getName());
                    pstmt.setString(2, reservation.getDate().toString());
                    pstmt.setString(3, reservation.getTime().toString());
                    return pstmt;
                }, keyHolder);
        int generatedKey = (int)(keyHolder.getKey().longValue());
        return new Reservation(generatedKey, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public void deleteById(int id){
        int updatedRowCount = jdbcTemplate.update(
                "delete from reservation where id = ?",
                id);
        if(updatedRowCount == 0){
            throw new NotFoundReservationException("예약이 존재하지 않습니다.");
        }
    }
}
