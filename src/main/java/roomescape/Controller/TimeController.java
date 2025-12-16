package roomescape.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Domain.Time;
import roomescape.Service.TimeService;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class TimeController {
    private TimeService timeService;
    public TimeController(TimeService timeService){
        this.timeService = timeService;
    }

    // render
    @GetMapping("/time")
    public String timePage() {
        return "time";
    }



    // Read
    @GetMapping("/times")
    @ResponseBody
    public List<Time> findAll(){
        return timeService.findAll();
    }

    // Create
    @PostMapping("/times")
    public ResponseEntity<Time> addTime(@RequestBody Time time){
        if(isBlank(time.getTime())){
            throw new BadRequestTimeException();
        }

        Long id = timeService.add(time);
        return ResponseEntity.created(URI.create("/times/"+id)).build();
    }

    //Delete
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int deleted = timeService.deleteByid(id);

        if (deleted == 0) {
            throw new NotFoundTimeException();
        }

        return ResponseEntity.noContent().build();
    }

    // Exception Handler
    public class NotFoundTimeException extends RuntimeException {}
    public class BadRequestTimeException extends RuntimeException {}
    @ExceptionHandler({TimeController.BadRequestTimeException.class, TimeController.NotFoundTimeException.class})
    public ResponseEntity<Void> handleBadRequest(RuntimeException e){
        return ResponseEntity.badRequest().build();
    }
}
