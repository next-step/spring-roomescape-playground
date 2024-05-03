package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationDTO;
import roomescape.entity.Reservations;
import roomescape.exception.NotFoundReservationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReservationRepo {
    private JdbcTemplate jdbcTemplate;
    private TimeRepo timeRepo;

    public ReservationRepo(JdbcTemplate jdbcTemplate, TimeRepo timeRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeRepo = timeRepo;
    }


    public class ReservationMapper implements RowMapper<Reservations> {
        @Override
        public Reservations mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Reservations(rs.getInt("id"), rs.getString("name"), rs.getString("date"), timeRepo.findById(rs.getLong("time_id")));
        }
    }


    public List<Reservations> findAll() {
        return jdbcTemplate.query("SELECT * FROM RESERVATIONS", new ReservationMapper());
    }

    public Reservations findById(long id) {
        String sql = "SELECT * FROM RESERVATIONS WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ReservationMapper(), id);
    }

    public int count() {
        String sql = "SELECT count(*) FROM RESERVATIONS";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int insert(ReservationDTO reservations) {
        String sql = "INSERT INTO RESERVATIONS (name, date, time_id) values(?, ?, ?)";
        return jdbcTemplate.update(sql, reservations.getName(), reservations.getDate(), reservations.getTimeId());
    }


    public void delete(long id) throws NotFoundReservationException {
        try {
            Reservations r = this.findById(id);
        } catch (Exception e) {
            throw new NotFoundReservationException();
        }

        String sql = "DELETE FROM RESERVATIONS WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}