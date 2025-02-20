package roomescape.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.domain.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.mapper.ReservationRowMapper;

@Repository
public class ReservationDAO {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationRowMapper reservationRowMapper;

    public ReservationDAO(JdbcTemplate jdbcTemplate, ReservationRowMapper reservationRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRowMapper = reservationRowMapper;
    }

    public List<Reservation> findReservations() {
        String sql = "select id, name, reservation_date, reservation_time from reservation";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public ReservationResponse createReservation(ReservationCreateRequest request) {
        String sql = "insert into reservation(name, reservation_date, reservation_time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String name = request.getName();
        LocalDate date = request.getDate();
        LocalTime time = request.getTime();

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement prepareStatement = connection.prepareStatement(sql, new String[]{"id"});
            prepareStatement.setString(1, name);
            prepareStatement.setDate(2, Date.valueOf(date));
            prepareStatement.setTime(3, Time.valueOf(time));
            return prepareStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new ReservationResponse(id, name, date, time);
    }

    public void deleteReservation(Long reservationId) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, reservationId);
    }
}