package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.dto.CheckReservationsResponse;
import roomescape.domain.dto.RequestReservation;
import roomescape.service.exception.NoReservationException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public JdbcReservationRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Reservation addReservation(RequestReservation request) {
        Map<String, String> params = new HashMap<>();
        params.put("name", request.name());
        params.put("date", request.date());
        params.put("time", request.time());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        Reservation reservation = new Reservation(id, request.name(), request.date(), request.time());

        return reservation;
    }

    @Override
    public List<CheckReservationsResponse> findAll() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    private final RowMapper<CheckReservationsResponse> reservationRowMapper = (resultSet, rowNum) -> {
        CheckReservationsResponse reservation = new CheckReservationsResponse(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );

        return reservation;
    };

    @Override
    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM RESERVATION WHERE ID = ?";

        int updateRowCount = jdbcTemplate.update(sql, id);
        if (updateRowCount == 0) {
            throw new NoReservationException("존재하지 않는 예약입니다.");
        }
    }
}
