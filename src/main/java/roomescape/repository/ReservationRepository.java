package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

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
                    "insert into reservation (name, date, time) values (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setString(3, reservation.getTime().toString());
            return ps;
        }, keyHolder);

        return requireNonNull(keyHolder.getKey(), "예약 생성에 실패했습니다. 예약 id가 존재하지 않습니다.").longValue();
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "select id, name, date, time from reservation",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getString("time")
                ));
    }

    public void deleteById(long deleteId) {
        int updatedCount = jdbcTemplate.update("delete from reservation where id = ?", deleteId);
        if (updatedCount == 0) {
            throw new NoSuchElementException("예약을 취소할 수 없습니다. 존재하지 않는 예약입니다.");
        }
    }
}
