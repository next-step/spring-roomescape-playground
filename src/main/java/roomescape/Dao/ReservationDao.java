package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReservationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public long addReservation(Reservation reservation) {
        Time time = reservation.getTime();
        long timeId;

        // Check if the time object has already been persisted
        if (time.getId() != null && time.getId() > 0) {
            timeId = time.getId();
        } else {
            // If not, add the time object to the database
            timeId = addTime(time);
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", timeId);

        return jdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    private long addTime(Time time) {
        SimpleJdbcInsert timeInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("time")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> timeParameters = new HashMap<>();
        timeParameters.put("time", time.getTime());

        return timeInsert.executeAndReturnKey(timeParameters).longValue();
    }

    public List<Reservation> getAllReservations() {
        return jdbcTemplate.query(
                "SELECT reservation.id, reservation.name, reservation.date, time.time " +
                        "FROM reservation " +
                        "INNER JOIN time ON reservation.time_id = time.id",
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new Time(resultSet.getLong("id"), resultSet.getString("time"))));
    }

    public Reservation getReservationById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT reservation.id, reservation.name, reservation.date, time.time " +
                        "FROM reservation " +
                        "INNER JOIN time ON reservation.time_id = time.id " +
                        "WHERE reservation.id = ?",
                new Object[]{id},
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new Time(resultSet.getLong("id"), resultSet.getString("time"))));
    }

    public int cancelReservation(Long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}