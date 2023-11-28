package roomescape.domain.reservation.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.global.BusinessException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static roomescape.global.ErrorCode.RESERVATION_NOT_FOUND;

@Repository
@AllArgsConstructor
public class ReservationRepositoryJdbc implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Reservation save(final String name, final LocalDate date, final LocalTime time) {
        String sql = "INSERT INTO RESERVATION (name, date, time) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, name);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setTime(3, Time.valueOf(time));
            return pstmt;
        }, keyHolder);

        return Reservation.builder()
                .id(keyHolder.getKey().longValue())
                .name(name)
                .date(date)
                .time(time)
                .build();
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT * FROM RESERVATION";
        return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Reservation.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .build())
                .toList();
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        String sql = "SELECT * FROM RESERVATION WHERE id = ?";
        return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Reservation.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .date(rs.getDate("date").toLocalDate())
                        .time(rs.getTime("time").toLocalTime())
                        .build(),
                        id)
                .findFirst();
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM RESERVATION WHERE id = ?";
        int rowCount = jdbcTemplate.update(sql, id);

        if (rowCount == 0) {
            throw new BusinessException(id, "id", RESERVATION_NOT_FOUND);
        }
    }
}
