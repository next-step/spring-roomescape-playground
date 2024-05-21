package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.service.ReservationExceptionHandler;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class ReservationRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public void addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public List<Reservation> findAll() {
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql,
                reservationRowMapper
        );
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new ReservationExceptionHandler.NoReservationException("존재하지 않는 예약입니다.");
        }
    }
}
