package roomescape.repository;

import jakarta.validation.constraints.Null;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.controller.ReservationController;
import roomescape.exception.NotFoundReservationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class ReservationRepo {
    private JdbcTemplate jdbcTemplate;

    public ReservationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public class ReservationMapper implements RowMapper<ReservationController.Reservations> {

        @Override
        public ReservationController.Reservations mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new ReservationController.Reservations(rs.getInt("id"),rs.getString("name"), rs.getDate("date").toLocalDate(), rs.getTime("time").toLocalTime());

        }
    }



    public List<ReservationController.Reservations> findAll() {
        return jdbcTemplate.query("SELECT * FROM RESERVATIONS", new ReservationMapper());
    }

    public ReservationController.Reservations findById(int id) {
        String sql = "SELECT * FROM RESERVATIONS WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ReservationMapper(), id);
    }

    public int count() {
            String sql = "SELECT count(*) FROM RESERVATIONS";
            return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int insert(ReservationController.Reservations reservations) {
        String sql = "INSERT INTO RESERVATIONS (name, date, time) values(?, ?, ?)";
        return jdbcTemplate.update(sql,  reservations.name, reservations.date, reservations.time);
    }

    public int delete(int id) throws NotFoundReservationException {
        try {
            ReservationController.Reservations r = this.findById(id);
        } catch (Exception e) {
            throw new NotFoundReservationException();
        }

        String sql = "DELETE FROM RESERVATIONS WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
