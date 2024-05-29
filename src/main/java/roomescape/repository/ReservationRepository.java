package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.domain.Reservation;
import roomescape.exception.DatabaseOperationException;
import roomescape.exception.ReservationNotFoundException;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reservation reservation = new Reservation();
            reservation.setId(rs.getLong("id"));
            reservation.setName(rs.getString("name"));
            reservation.setDate(rs.getString("date"));
            reservation.setTime(rs.getString("time"));
            return reservation;
        }
    }

    public List<Reservation> getAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, new ReservationRowMapper());
    }

    public Reservation getReservationById(long id){
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ReservationRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException("Reservation with id " + id + " not found");
        } catch (Exception e) {
            throw new DatabaseOperationException();
        }
    }

    public long saveReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try{
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservation.getName());
                ps.setString(2, reservation.getDate());
                ps.setString(3, reservation.getTime());
                return ps;
            }, keyHolder);

            reservation.setId(keyHolder.getKey().longValue());
            return reservation.getId();
        } catch (Exception e){
            throw new DatabaseOperationException();
        }
    }

    public void deleteReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        try{
            jdbcTemplate.update(sql, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ReservationNotFoundException("Reservation with id " + id + " not found");
        } catch (Exception e) {
            throw new DatabaseOperationException();
        }
    }
}
