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
import java.util.List;
import java.util.Optional;

import static roomescape.global.ErrorCode.RESERVATION_NOT_FOUND;

@Repository
@AllArgsConstructor
public class ReservationRepositoryJdbc implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TimesRepositoryJdbc timesRepositoryJdbc;

    @Override
    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO RESERVATION (name, date, time_id) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reservation.getName());
            pstmt.setDate(2, Date.valueOf(reservation.getDate()));
            pstmt.setString(3, String.valueOf(reservation.getTime().getId()));
            return pstmt;
        }, keyHolder);

        return Reservation.builder()
                .id(keyHolder.getKey().longValue())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .build();
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "SELECT * FROM RESERVATION";
        return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Reservation.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .date(rs.getDate("date").toLocalDate())
                        .time(
                                timesRepositoryJdbc.findById(Long.valueOf(rs.getString("time_id")))
                                        .orElseThrow(RuntimeException::new)
                        )
                        .build())
                .toList();
    }

    @Override
    public Optional<Reservation> findById(final Long id) {
        final String sql = "SELECT * FROM RESERVATION WHERE id = ?";
        return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Reservation.builder()
                                .id(rs.getLong("id"))
                                .name(rs.getString("name"))
                                .date(rs.getDate("date").toLocalDate())
                                .time(
                                        timesRepositoryJdbc.findById(Long.valueOf(rs.getString("time_id")))
                                                .orElseThrow(RuntimeException::new)
                                )
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
