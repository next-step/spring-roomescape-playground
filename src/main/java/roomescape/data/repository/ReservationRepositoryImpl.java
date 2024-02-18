package roomescape.data.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.common.exception.ReservationErrorCode;
import roomescape.common.exception.ReservationException;
import roomescape.data.dao.ReservationTimeDaoImpl;
import roomescape.data.repository.repositoryInterface.ReservationRepository;
import roomescape.data.dto.ReservationRequest;
import roomescape.data.entity.Reservation;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ReservationTimeDaoImpl reservationTimeDao;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate, ReservationTimeDaoImpl reservationTimeDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public List<Reservation> findAll() {
        final String sql = "select * from reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Reservation reservation = new Reservation(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    reservationTimeDao.findById(Long.valueOf(resultSet.getString("time_id")))
            );
            return reservation;
        });
    }

    @Override
    public Reservation save(ReservationRequest reservationRequest) {
        if (reservationRequest == null ||
                reservationRequest.getName().isBlank() ||
                reservationRequest.getDate().isBlank()
        ) {
            throw new ReservationException(ReservationErrorCode.INVALID_ARGUMENT_ERROR);
        }
        long timeId = reservationRequest.getTimeId();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";
        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, reservationRequest.getName());
                ps.setString(2, reservationRequest.getDate());
                ps.setString(3, String.valueOf(timeId));
                return ps;
        }, keyHolder);
        return Reservation.builder()
                .id(keyHolder.getKey().longValue())
                .date(reservationRequest.getDate())
                .name(reservationRequest.getName())
                .time(reservationTimeDao.findById(timeId))
                .build();
    }

    @Override
    public void deleteById(Long id) {
        final String sql = "delete from reservation where id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new ReservationException(ReservationErrorCode.NO_DELETE_RESERVATION_FOUND);
        }
    }
}
