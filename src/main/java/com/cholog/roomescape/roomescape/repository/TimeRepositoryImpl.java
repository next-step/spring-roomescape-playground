package com.cholog.roomescape.roomescape.repository;

import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.exception.conflict.TimeConflictException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.cholog.roomescape.roomescape.enums.RoomEscapeExceptionCode.TIME_CONFLICT;
import static com.cholog.roomescape.roomescape.enums.RoomEscapeExceptionCode.TIME_IN_USE;

@Repository
public class TimeRepositoryImpl implements TimeRepository {

    private static final RowMapper<Time> TIME_ROW_MAPPER =
            (resultSet, rowNum) -> Time.withId(
                    resultSet.getLong("id"),
                    new Time(
                            resultSet.getObject("time", LocalTime.class)
                    )
            );

    private JdbcTemplate jdbcTemplate;

    public TimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Time save(Time time) {
        // JDBC API에서 반환할 기본 키 바인더
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(
                    // PreparedStatementCreator 익명 객체 빌드
                    connection -> {
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "insert into time(time) values(?)",
                                new String[]{"id"}
                        );

                        preparedStatement.setString(1, time.getTime().toString());

                        return preparedStatement;
                    }, keyHolder
            );
        } catch (DuplicateKeyException e) {
            throw new TimeConflictException(TIME_CONFLICT.getMessage());
        }

        Long id = keyHolder.getKey().longValue();
        return Time.withId(id, time);
    }

    @Override
    public List<Time> findAll() {
        return jdbcTemplate.query(
                "select id, time from time",
                TIME_ROW_MAPPER
        );
    }

    @Override
    public void delete(Time time) {
        if (time == null) {
            throw new IllegalArgumentException();
        }
        try {
            jdbcTemplate.update(
                    "delete from time where id = ?",
                    time.getId()
            );
        } catch (DataIntegrityViolationException e) {
            throw new TimeConflictException(TIME_IN_USE.getMessage());
        }
    }

    @Override
    public Optional<Time> findById(Long timeId) {
        return jdbcTemplate.query(
                "select t.id, t.time from time as t where t.id = ?",
                TIME_ROW_MAPPER,
                timeId
        ).stream().findFirst();
    }

    @Override
    public Optional<Time> findByTime(LocalTime time) {
        return jdbcTemplate.query(
                "select id, time from time where time = ?",
                TIME_ROW_MAPPER,
                time
        ).stream().findFirst();
    }
}
