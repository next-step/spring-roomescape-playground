package roomescape.reservation.dao;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import roomescape.global.domain.DomainException;
import roomescape.reservation.domain.CreateReservationInfo;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationException;
import roomescape.reservation.domain.ReservationId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationsDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationsDao(@Nonnull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowToReservation = (result, rowNum) -> new Reservation(
            new ReservationId(result.getLong("id")),
            result.getString("name"),
            result.getTimestamp("time").toLocalDateTime()
    );

    public @Nonnull List<Reservation> getAll() {
        try {
            String querySql = "SELECT id, name, time FROM reservations";
            return jdbcTemplate.query(querySql, rowToReservation);
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public @Nullable Reservation get(@Nonnull ReservationId id) {
        String findUniqueSql = "SELECT id, name, time FROM reservations WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(findUniqueSql, rowToReservation, id.id());
        } catch (EmptyResultDataAccessException emptyResultData) {
            return null;
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

    }

    public @Nullable Reservation getByTime(@Nonnull LocalDateTime time) {
        String findSql = "SELECT id, name, time FROM reservations WHERE time = ?";
        try {
            return jdbcTemplate.queryForObject(findSql, rowToReservation, time);
        } catch (EmptyResultDataAccessException emptyResultData) {
            return null;
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    @Transactional
    public @Nonnull Reservation create(@Nonnull CreateReservationInfo info) {
        ReservationId id = insert(info);
        Reservation reservation = get(id);
        if (reservation == null) {
            throw new DomainException.UnknownError("예약을 생성했지만, 해당 예약을 찾을 수 없습니다.");
        }
        return reservation;
    }

    private @Nonnull ReservationId insert(@Nonnull CreateReservationInfo info) {
        String insertSql = "INSERT INTO reservations (name, time) VALUES (?, ?)";
        KeyHolder keyHolder;

        try {
            keyHolder = new GeneratedKeyHolder();
            int affectedRows = jdbcTemplate.update((connection) -> {
                var statement = connection.prepareStatement(insertSql, new String[]{"id"});
                statement.setString(1, info.name());
                statement.setTimestamp(2, Timestamp.valueOf(info.time()));
                return statement;
            }, keyHolder);

            if (affectedRows != 1) {
                throw new DomainException.UnknownError("reservation 행을 삽입할 수 없습니다.");
            }
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

        long idValue = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new ReservationId(idValue);
    }

    public void delete(@Nonnull ReservationId id) {
        String deleteSql = "DELETE FROM reservations WHERE id = ?";
        int affectedRows;
        try {
            affectedRows = jdbcTemplate.update(deleteSql, id.id());
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

        if (affectedRows == 0) {
            throw new ReservationException.DoesNotExist();
        }
    }
}
