package roomescape.reservation.repository;

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
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeId;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReservationsRepository(@Nonnull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_RESERVATION_QUERY = """
            SELECT r.id, r.name, r.date, t.id as time_id, t.time as time_value
            FROM reservation AS r
            INNER JOIN time AS t ON r.time_id = t.id
            """;

    private final RowMapper<Reservation> rowToReservation = (result, rowNum) -> {
        Time time = new Time(
                new TimeId(result.getLong("time_id")),
                result.getTime("time_value").toLocalTime()
        );
        return new Reservation(
                new ReservationId(result.getLong("id")),
                result.getString("name"),
                result.getDate("date").toLocalDate(),
                time
        );
    };

    public @Nonnull List<Reservation> getAll() {
        try {
            return jdbcTemplate.query(SELECT_RESERVATION_QUERY, rowToReservation);
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public @Nonnull Reservation get(@Nonnull ReservationId id) {
        String findUniqueSql = SELECT_RESERVATION_QUERY + " WHERE r.id = ?";
        try {
            Reservation reservation = jdbcTemplate.queryForObject(findUniqueSql, rowToReservation, id.id());
            if (reservation == null) {
                throw new ReservationException.DoesNotExist();
            }
            return reservation;
        } catch (EmptyResultDataAccessException emptyResultData) {
            throw new ReservationException.DoesNotExist();
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

    }

    public @Nullable Reservation getByDateTime(@Nonnull LocalDate date, @Nonnull TimeId timeId) {
        String findSql = SELECT_RESERVATION_QUERY + " WHERE date = ? AND time_id = ?";
        try {
            return jdbcTemplate.queryForObject(findSql, rowToReservation, date, timeId.id());
        } catch (EmptyResultDataAccessException emptyResultData) {
            return null;
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    @Transactional
    public @Nonnull Reservation create(@Nonnull CreateReservationInfo info) {
        ReservationId id = createAndGetId(info);
        return get(id);
    }

    private @Nonnull ReservationId createAndGetId(@Nonnull CreateReservationInfo info) {
        String insertSql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder;

        try {
            keyHolder = new GeneratedKeyHolder();
            int affectedRows = jdbcTemplate.update((connection) -> {
                var statement = connection.prepareStatement(insertSql, new String[]{"id"});
                statement.setString(1, info.name());
                statement.setDate(2, java.sql.Date.valueOf(info.date()));
                statement.setLong(3, info.timeId().id());
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
        String deleteSql = "DELETE FROM reservation WHERE id = ?";
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
