package roomescape.dataLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationDto;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.dataLayer.errors.ReservationNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getReservationList() {
        return jdbcTemplate.query("SELECT * FROM reservation left join time as t on reservation.time_id = t.id;",
                this::extractReservationFromResultSet);
    }

    public Long add(ReservationDto reservationDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation (name, date, time_id) values (?, ? ,?)",
                    new String[]{"id"});
            ps.setString(1, reservationDto.name());
            ps.setString(2, reservationDto.date());
            ps.setLong(3, Long.parseLong(reservationDto.time()));

            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation getReservationById(Long id) {
        List<Reservation> possibleReservation = jdbcTemplate.query("SELECT * FROM reservation as r left join time as t on r.time_id = t.id WHERE r.id = ?", this::extractReservationFromResultSet, id);

        if (possibleReservation.isEmpty()) {
            throw new ReservationNotFoundException("존재하지 않는 예약입니다");
        }

        return possibleReservation.get(0);
    }

    public void removeById(long deletingId) {
        int deletedRowCounts = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", deletingId);

        if (deletedRowCounts == 0) {
            throw new ReservationNotFoundException("존재하지 않은 예약건입니다.");
        }
    }

    private Reservation extractReservationFromResultSet(ResultSet resultSet, int rowNum) throws SQLException {
        Time reservationTime = new Time(resultSet.getLong("id"),
                resultSet.getString("time"));

        return new Reservation(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                reservationTime);
    }
}

