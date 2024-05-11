package roomescape.dao;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationDto;
import roomescape.dto.TimeDto;
import roomescape.exception.CustomException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationDAO {

    private final JdbcTemplate jdbcTemplate;
    private TimeDAO timeDAO;

    public ReservationDAO(JdbcTemplate jdbcTemplate, TimeDAO timeDAO){
        this.jdbcTemplate = jdbcTemplate;
        this.timeDAO = timeDAO;
    }


    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {

        return Reservation.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .date(LocalDate.parse(resultSet.getString("date")))
                .time(timeDAO.findTimeById(resultSet.getLong("time_id")))
                .build();
    };

    public List<Reservation> getReservations() { //List형식의 Reservation을 반환
        try {
            return jdbcTemplate.query("SELECT * FROM RESERVATION", rowMapper);
        } catch (DataAccessException exception) {
            throw new CustomException();
        }

    }

    public Reservation getReservationById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM reservation where id = ?", rowMapper, id);
        } catch (DataAccessException exception) {
            throw new CustomException();
        }
    }

    public int postReservation(ReservationDto.ReservationRequest reservationDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservationDto.getName());
                ps.setString(2, reservationDto.getDate().toString());
                ps.setLong(3, reservationDto.getTime());
                return ps;
            }, keyHolder);
            return keyHolder.getKey().intValue(); // 자동 생성된 키 값 반환
        } catch (DataAccessException exception) {
            System.out.println(exception.toString());
            throw new CustomException();
        }

    }

    public void deleteReservations(Long id) {
        try {
            jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
        } catch (DataAccessException exception) {
            throw new CustomException();
        }
    }

}
