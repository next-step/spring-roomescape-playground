package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationVO;
import roomescape.exception.CustomException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationVO> rowMapper = (resultSet, rowNum) -> {

        return ReservationVO.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .date(LocalDate.parse(resultSet.getString("date")))
                .time(LocalTime.parse(resultSet.getString("time")))
                .build();
    };

    public List<ReservationVO> getReservations() {
        try {
            return jdbcTemplate.query("SELECT * FROM reservation", rowMapper);
        } catch (DataAccessException exception) {
            throw new CustomException();
        }

    }

    public ReservationVO getReservationById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM reservation where id = ?", rowMapper, id);
        } catch (DataAccessException exception) {
            throw new CustomException();
        }

    }

    public int postReservation(ReservationVO reservationVO) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, reservationVO.getName());
                ps.setString(2, reservationVO.getDate().toString());
                ps.setString(3, reservationVO.getTime().toString());
                return ps;
            }, keyHolder);
            return keyHolder.getKey().intValue(); // 자동 생성된 키 값 반환
        } catch (DataAccessException exception) {
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
