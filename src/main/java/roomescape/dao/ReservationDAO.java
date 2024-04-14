package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationVO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationDAO{

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
        return jdbcTemplate.query("SELECT * FROM reservation", rowMapper);
    }

    public Optional<ReservationVO> getReservationbyId(Long id){
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM reservation where id = ?", rowMapper , id));
    }

    public int postReservation(ReservationVO reservationVO){
        return jdbcTemplate.update("INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)",reservationVO.getName(), reservationVO.getDate(), reservationVO.getTime());
    }



    public void deleteReservations(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }



}
