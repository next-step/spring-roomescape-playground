package roomescape.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationDto;
import roomescape.model.errors.ReservationNotFoundException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class Reservations {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Reservations(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getReservationList() {
        return jdbcTemplate.query("SELECT * FROM reservation", Reservation::new);
    }

    public Reservation add(ReservationDto reservationDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation (name, date, time) values (?, ? ,?)",
                    new String[]{"id"});
            ps.setString(1, reservationDto.name());
            ps.setString(2, reservationDto.date());
            ps.setString(3, reservationDto.time());

            return ps;
        }, keyHolder);

        long newId = keyHolder.getKey().longValue();
        return jdbcTemplate.queryForObject("SELECT * FROM reservation WHERE id = ?", Reservation::new, newId);

    }

    public void removeById(long deletingId) throws ReservationNotFoundException {
        int deletedRowCounts = jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", deletingId);

        if (deletedRowCounts == 0) {
            throw new ReservationNotFoundException();
        }
    }
}

