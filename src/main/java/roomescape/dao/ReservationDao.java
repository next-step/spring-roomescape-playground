package roomescape.dao;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationAddReq;
import roomescape.dto.ReservationReq;

import java.util.List;

@Repository
public class ReservationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //예약 조회
    public List<ReservationReq> findAll(){
        String sql="SELECT * from reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet,rowNum)->{
                    ReservationReq reservation = new ReservationReq(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getTime("time")
                    );
                    return reservation;
                });
    }

    //예약 추가
    public ReservationReq addReservation(ReservationAddReq reservation) {
        String sql = "SELECT \n" +
                "r.id as reservation_id, \n" +
                "r.name, \n" +
                "r.date, \n" +
                "t.id as time_id, \n" +
                "t.time as time_value \n" +
                "FROM reservation as r inner join time as t on r.time_id = t.id\n";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
        String getIdSql = "SELECT LAST_INSERT_ID()"; //해당 구문 사용 위해 application.properties에 MODE=MySQL 추가함
        Long id = jdbcTemplate.queryForObject(getIdSql, Long.class);
        ReservationReq newReservation = new ReservationReq(id, reservation.getName(), reservation.getDate(), reservation.getTime());
        return newReservation;
    }

    //예약 삭제
    public int deleteReservation(Long id){
        String sql="DELETE FROM reservation WHERE id=?";
        return jdbcTemplate.update(sql,id);
    }

    }
