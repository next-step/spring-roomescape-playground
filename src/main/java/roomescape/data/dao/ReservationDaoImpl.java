package roomescape.data.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.common.exception.ReservationErrorCode;
import roomescape.common.exception.ReservationException;
import roomescape.data.dao.daoInterface.ReservationDao;
import roomescape.data.dto.ReservationRequest;
import roomescape.data.dto.ReservationResponse;

@Component
public class ReservationDaoImpl implements ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ReservationResponse> getReservations() {
        final String sql = "select * from reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ReservationResponse reservationResponse = new ReservationResponse(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    resultSet.getString("time")
            );
            return reservationResponse;
        });
    }

    @Override
    public long createReservation(ReservationRequest reservationRequest) {
        if (reservationRequest == null ||
                reservationRequest.getName().isBlank() ||
                reservationRequest.getDate().isBlank() ||
                reservationRequest.getTime().isBlank()) {
            throw new ReservationException(ReservationErrorCode.INVALID_ARGUMENT_ERROR);
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, reservationRequest.getName());
                ps.setString(2, reservationRequest.getDate());
                ps.setString(3, reservationRequest.getTime());
                return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void deleteReservation(Long id) {
        final String sql = "delete from reservation where id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new ReservationException(ReservationErrorCode.NO_DELETE_RESERVATION_FOUND);
        }
    }
}
