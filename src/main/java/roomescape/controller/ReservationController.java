package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationAddReq;
import roomescape.dto.ReservationReq;
import roomescape.exception.InvalidRequestReservationException;
import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    //AtomicLong: Long 자료형 가지는 Wrapping 클래스. incrementAndGet(): ++x
    private AtomicLong index = new AtomicLong(0);
    private List<ReservationReq> reservations1 = new ArrayList<>();
    private JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @GetMapping("/reservation")
    public String reservation(Model model){

        model.addAttribute(reservations1);
        return "reservation";
    }

    //예약 조회
    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationReq> reservations(){
        String sql="SELECT * from reservation";
        List<ReservationReq> reservations=jdbcTemplate.query(
                sql,
                (resultSet,rowNum)->{
                    ReservationReq reservation = new ReservationReq(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return reservation;
                });
        return reservations;
    }

    //예약 추가
    @PostMapping("/reservations")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationReq addReservation(@RequestBody ReservationAddReq reservation, HttpServletResponse response){
        if(reservation.getName().isEmpty()||reservation.getDate().isEmpty()||reservation.getTime().isEmpty()){
            throw new InvalidRequestReservationException("필요한 인자가 없습니다.");
        }

        String sql="INSERT INTO reservation(name, date, time) VALUES (?,?,?)";
        jdbcTemplate.update(sql,reservation.getName(),reservation.getDate(),reservation.getTime());
        String getIdSql="SELECT LAST_INSERT_ID()"; //해당 구문 사용 위해 application.properties에 MODE=MySQL 추가함
        Long id=jdbcTemplate.queryForObject(getIdSql, Long.class);
        ReservationReq newReservation =new ReservationReq(id,reservation.getName(),reservation.getDate(),reservation.getTime());

        // ResponseEntity 사용하면 header 명시적으로 지정하지 않아도 된다고 한다.
        response.setHeader("Location", "/reservations/" + id);
        return newReservation;
    }

    //예약 삭제
    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id){

        String sql="DELETE FROM reservation WHERE id=?";
        int rowCount=jdbcTemplate.update(sql,id);
        if(rowCount==0){
            throw new NotFoundReservationException("삭제할 예약이 없습니다");
        }

    }

}