package com.cholog.roomescape.roomescape.repository;

import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.entity.Time;
import com.cholog.roomescape.roomescape.exception.badrequest.TimeNotValidException;
import com.cholog.roomescape.roomescape.exception.conflict.ReservationConflictException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.cholog.roomescape.roomescape.enums.RoomEscapeExceptionCode.RESERVATION_CONFLICT;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER =
            (resultSet, rowNum) -> Reservation.withId(
                    resultSet.getLong("id"),
                    new Reservation(
                            resultSet.getString("name"),
                            resultSet.getObject("date", LocalDate.class),
                            Time.withId(
                                    resultSet.getLong("time_id"),
                                    new Time(resultSet.getObject("time", LocalTime.class))
                            )
                    )
            );

    private JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        // JDBC API에서 반환할 기본 키 바인더
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(
                    // PreparedStatementCreator 익명 객체 빌드
                    connection -> {
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "insert into reservation(name, date, time_id) values (?, ?, ?)",
                        new String[]{"id"}
                );

                // JDBC row 인덱싱은 1부터 시작. 값 바인딩
                preparedStatement.setString(1, reservation.getName());
                preparedStatement.setString(2, reservation.getDate().toString());
                preparedStatement.setString(3, reservation.getTime().getId().toString());

                return preparedStatement;
            },  keyHolder);
        } catch (DuplicateKeyException e) {
            throw new ReservationConflictException(RESERVATION_CONFLICT.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new TimeNotValidException(reservation.getTime().toString());
        }

        Long id = keyHolder.getKey().longValue();

        return Reservation.withId(id, reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                """
                        select r.id, r.name, date, t.id as time_id, t.time
                        from reservation as r
                         join time as t on r.time_id = t.id
                     """,
                RESERVATION_ROW_MAPPER
        );
    }

    @Override
    public void delete(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException();
        }
        jdbcTemplate.update(
                "delete from reservation where id = ?",
                reservation.getId()
        );
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return jdbcTemplate.query(
                """
                    select r.id, r.name, r.date, t.id as time_id, t.time
                    from reservation as r
                        join time as t on r.time_id = t.id
                    where r.id = ?
                """,
                RESERVATION_ROW_MAPPER,
                reservationId).stream().findFirst();
    }
}
