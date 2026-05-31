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

    private final RowMapper<TimeId> rowToTimeId = (result, rowNum) ->
            new TimeId(result.getLong("id"));

    private final RowMapper<Time> rowToTime = (result, rowNum) -> new Time(
            new TimeId(result.getLong("id")),
            result.getTime("time").toLocalTime()
    );

    public @Nonnull List<Time> getAll() {
        try {
            String querySql = "SELECT id, time FROM time";
            return jdbcTemplate.query(querySql, rowToTime);
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public @Nonnull Time get(@Nonnull TimeId id) {
        String findUniqueSql = "SELECT id, time FROM time WHERE id = ?";
        try {
            Time time = jdbcTemplate.queryForObject(findUniqueSql, rowToTime, id.id());
            if(time == null) {
                throw new TimeException.DoesNotExist();
            }
            return time;
        } catch (EmptyResultDataAccessException emptyResultData) {
            throw new TimeException.DoesNotExist();
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public boolean has(@Nonnull TimeId id) {
        String existsSql = "SELECT EXISTS (SELECT * FROM time WHERE id = ?)";
        try {
            return jdbcTemplate.queryForObject(existsSql, Boolean.TYPE, id.id());
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public @Nullable TimeId getIdAt(@Nonnull LocalTime time) {
        String findSql = "SELECT id FROM time WHERE time = ?";
        try {
            return jdbcTemplate.queryForObject(findSql, rowToTimeId, time);
        } catch (EmptyResultDataAccessException emptyResultData) {
            return null;
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }
    }

    public @Nullable Time getAt(@Nonnull LocalTime time) {
        String findSql = "SELECT id, time FROM time WHERE time = ?";
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
        return get(id);
    }

    private @Nonnull TimeId createAndGetId(@Nonnull CreateTimeInfo info) {
        String insertSql = "INSERT INTO time (time) VALUES (?)";
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
        } catch (DuplicateKeyException e) {
            throw new TimeException.DuplicateTime();
        } catch (DataAccessException e) {
            throw new DomainException.UnknownError(e);
        }

        long idValue = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new TimeId(idValue);
    }

    public void delete(@Nonnull TimeId id) {
        String deleteSql = "DELETE FROM time WHERE id = ?";
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
