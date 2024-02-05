package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import roomescape.domain.Time;
import roomescape.dto.ReservationDTO;

@Repository
public class ReservationQueryingDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationQueryingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationDTO> reservationRowMapper = (resultSet, rowNum) -> {
        final Long id = resultSet.getLong("id");
        final Long timeId = resultSet.getLong("time_id");
        final String timeValue = resultSet.getString("time_value");

        final Time time = new Time(timeId, timeValue);

        final ReservationDTO reservation = new ReservationDTO(
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );

        return reservation.toEntity(id, reservation.getName(), reservation.getDate(), reservation.getTime());

    };

    public List<ReservationDTO> getAllReservations(){
        String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.time as time_value \n" +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
