package roomescape.time.repository;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import roomescape.global.domain.DomainException;
import roomescape.time.domain.CreateTimeInfo;
import roomescape.time.domain.Time;
import roomescape.time.domain.TimeException;
import roomescape.time.domain.TimeId;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class TimesRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimesRepository(@Nonnull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> rowToTime = (result, rowNum) -> new Time(
            new TimeId(result.getLong("id")),
            result.getTime("time").toLocalTime()
    );

    public @Nonnull List<Time> getAll() {
        try {
            String querySql = "SELECT id, time FROM times";
            return jdbcTemplate.query(querySql, rowToTime);
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public @Nullable Time get(@Nonnull TimeId id) {
        String findUniqueSql = "SELECT id, time FROM times WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(findUniqueSql, rowToTime, id.id());
        } catch (EmptyResultDataAccessException emptyResultData) {
            return null;
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

    }

    public @Nullable Time getByTime(@Nonnull LocalTime time) {
        String findSql = "SELECT id, time FROM times WHERE time = ?";
        try {
            return jdbcTemplate.queryForObject(findSql, rowToTime, time);
        } catch (EmptyResultDataAccessException emptyResultData) {
            return null;
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public @Nonnull Time create(@Nonnull CreateTimeInfo info) {
        TimeId id = createAndGetId(info);
        Time reservation = get(id);
        if (reservation == null) {
            throw new DomainException.UnknownError("예약을 생성했지만, 해당 예약을 찾을 수 없습니다.");
        }
        return reservation;
    }

    private @Nonnull TimeId createAndGetId(@Nonnull CreateTimeInfo info) {
        String insertSql = "INSERT INTO times (time) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            int affectedRows = jdbcTemplate.update((connection) -> {
                var statement = connection.prepareStatement(insertSql, new String[]{"id"});
                statement.setTime(1, java.sql.Time.valueOf(info.time()));
                return statement;
            }, keyHolder);

            if (affectedRows != 1) {
                throw new DomainException.UnknownError("reservation 행을 삽입할 수 없습니다.");
            }
        } catch(DuplicateKeyException e) {
            throw new TimeException.DuplicateTime();
        } catch(DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

        long idValue = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new TimeId(idValue);
    }

    public void delete(@Nonnull TimeId id) {
        String deleteSql = "DELETE FROM times WHERE id = ?";
        int affectedRows;
        try {
            affectedRows = jdbcTemplate.update(deleteSql, id.id());
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

        if (affectedRows == 0) {
            throw new TimeException.DoesNotExist();
        }
    }
}
