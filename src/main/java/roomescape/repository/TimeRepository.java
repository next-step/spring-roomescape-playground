package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.dto.RequestTime;
import roomescape.domain.dto.ResponseTime;
import roomescape.service.ReservationExceptionHandler;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public TimeRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("SettingTime")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public ResponseTime addTime(RequestTime requestTime) {
        Map<String, String> params = new HashMap<>();
        params.put("time", requestTime.time());

        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new ResponseTime(id, requestTime.time());
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
