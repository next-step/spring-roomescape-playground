package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.dto.CheckReservationsResponse;
import roomescape.domain.dto.RequestReservation;
import roomescape.service.exception.NoReservationException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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

        final String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.name());
            ps.setObject(2, request.date());
            ps.setLong(3, request.time().getId());

            return ps;
        }, keyHolder);

        final long generatedKey = keyHolder.getKey().longValue();

        Reservation reservation = new Reservation(generatedKey, request.name(), request.date(), request.time());

        return reservation;
    }

    @Override
    public List<CheckReservationsResponse> findAll() {
        String sql = "SELECT" +
                        "r.id as reservation_id, \n" +
                        "    r.name, \n" +
                        "    r.date, \n" +
                        "    t.id as time_id, \n" +
                        "    t.time as time_value \n" +
                        "FROM reservation as r inner join time as t on r.time_id = t.id";

        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    private final RowMapper<CheckReservationsResponse> reservationRowMapper = (resultSet, rowNum) -> {
        CheckReservationsResponse reservation = new CheckReservationsResponse(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new Time(resultSet.getLong("time_id"), resultSet.getString("time_value"))
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
