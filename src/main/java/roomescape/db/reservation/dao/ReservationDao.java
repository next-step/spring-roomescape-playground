package roomescape.db.reservation.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.api.exception.NotFoundException;
import roomescape.db.reservation.entity.ReservationEntity;
import roomescape.db.time.entity.TimeEntity;
import roomescape.enums.ErrorMessage;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private static final int DATABASE_ERROR = 0;

    public List<ReservationEntity> getReservations() {
        final String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value FROM reservation as r inner join time as t on r.time_id = t.id";

        return jdbcTemplate.query(
                sql, (resultSet, rowNum) -> ReservationEntity.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .date(resultSet.getString("date"))
                        .time(new TimeEntity(resultSet.getLong("time_id"), resultSet.getString("time_value")))
                        .build());
    }

    public ReservationEntity createReservations(final String name, final String date, final Long timeId) {
        final String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        final String findTimeSql = "INSERT INTO time(time) VALUES (?)";
        final String findSql = "SELECT id, name, date, time_id FROM reservation WHERE id = (?)";
        return jdbcTemplate.queryForObject(
                findSql,
                (resultSet, rowNum) -> {
                    final long findTimeId = resultSet.getLong("time_id");
                    final String findTime = jdbcTemplate.queryForObject(findTimeSql, String.class, findTimeId);

                    return ReservationEntity.builder()
                            .id(resultSet.getLong("id"))
                            .name(resultSet.getString("name"))
                            .date(resultSet.getString("date"))
                            .time(new TimeEntity(findTimeId, findTime)) // time_id 대신 실제 시간 반환
                            .build();
                },
                keyHolder.getKey().longValue()
        );
    }

    public void deleteReservations(final long reservationId) {
        final String sql = "DELETE FROM reservation WHERE id = ?";

        if (jdbcTemplate.update(sql, reservationId) == DATABASE_ERROR) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND_RESERVATION);
        }
    }
}
