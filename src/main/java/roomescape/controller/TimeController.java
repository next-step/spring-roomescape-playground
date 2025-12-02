package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeAddReq;
import roomescape.dto.TimeReq;
import roomescape.exception.InvalidRequestReservationException;
import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TimeController {

    private JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //시간 추가
    @PostMapping("/times")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public TimeReq addTime(@RequestBody TimeAddReq time, HttpServletResponse response){
        if(time.getTime().isEmpty()){
            throw new InvalidRequestReservationException("필요한 인자가 없습니다.");
        }

        String sql="INSERT INTO time(time) VALUES (?)";
        jdbcTemplate.update(sql,time.getTime());
        String getIdSql="SELECT LAST_INSERT_ID()";
        Long id=jdbcTemplate.queryForObject(getIdSql, Long.class);
        TimeReq newTime =new TimeReq(id, time.getTime());

        response.setHeader("Location", "/times/" + id);
        return newTime;
    }

    //시간 조회
    @GetMapping("/times")
    @ResponseBody
    public List<TimeReq> times(){
        String sql="SELECT * from time";
        List<TimeReq> times=jdbcTemplate.query(
                sql,
                (resultSet,rowNum)->{
                    TimeReq time = new TimeReq(
                            resultSet.getLong("id"),
                            resultSet.getString("time")
                    );
                    return time;
                });
        return times;
    }

    //시간 삭제
    @DeleteMapping("/times/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTime(@PathVariable Long id){
        String sql="DELETE FROM time WHERE id=?";
        int rowCount=jdbcTemplate.update(sql,id);
        if(rowCount==0){
            throw new NotFoundReservationException("삭제할 시간이 없습니다");
        }

    }
}
