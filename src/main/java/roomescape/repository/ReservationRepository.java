package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Reservation reservation) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time_id) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        return requireNonNull(keyHolder.getKey(), "예약 생성에 실패했습니다. 예약 id가 존재하지 않습니다.").longValue();
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                """
                        SELECT 
                            r.id as reservation_id,
                            r.name,
                            r.date,
                            t.id as time_id,
                            t.time as time_value 
                        FROM reservation as r inner join time as t on r.time_id = t.id
                        """,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("time_value"))
                ));
    }

    public void deleteById(long deleteId) {
        int updatedCount = jdbcTemplate.update("delete from reservation where id = ?", deleteId);
        if (updatedCount == 0) {
            throw new NoSuchElementException("예약을 취소할 수 없습니다. 존재하지 않는 예약입니다.");
        }
    }
}
