package com.cholog.roomescape.roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.cholog.roomescape.roomescape.entity.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private static final RowMapper<Reservation> RESERVATION_ROW_MAPPER =
            (resultSet, rowNum) -> Reservation.withId(
                    resultSet.getLong("id"),
                    new Reservation(
                            resultSet.getString("name"),
                            resultSet.getObject("date", LocalDate.class),
                            resultSet.getObject("time", LocalTime.class)
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

        jdbcTemplate.update(
                // PreparedStatementCreator 익명 객체 빌드
                connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "insert into reservation(name, date, time) values (?, ?, ?)",
                    new String[]{"id"}
            );

            // JDBC row 인덱싱은 1부터 시작. 값 바인딩
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setString(3, reservation.getTime().toString());

            return preparedStatement;
        },  keyHolder);

        Long id = keyHolder.getKey().longValue();

        return Reservation.withId(id, reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(
                "select id, name, date, time from reservation",
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
                "select id, name, date, time from reservation where id = ?",
                RESERVATION_ROW_MAPPER,
                reservationId).stream().findFirst();
    }
}
