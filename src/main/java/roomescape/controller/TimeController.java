package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import roomescape.dao.TimeQueryingDAO;
import roomescape.dao.TimeUpdatingDAO;
import roomescape.domain.Time;
import roomescape.service.TimeService;

@RestController
public class TimeController {

    TimeService timeservice;

    public TimeController(TimeService timeservice){
        this.timeservice = timeservice;
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody Time time){
        if(time.getTime().isEmpty()||time.getTime()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Time newTime = timeservice.save(time);

        return ResponseEntity
                .status(201)
                .location(java.net.URI.create("/times/"+newTime.getId()))
                .body(newTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes(){

        List<Time> times = timeservice.findAllTimes();

        return ResponseEntity.ok().body(times);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Time> deleteTime(@PathVariable long id) {

        int row = timeservice.delete(id);

        if(row>0){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
