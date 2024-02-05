package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import roomescape.domain.Time;
import roomescape.dto.TimeAddRequest;
import roomescape.exception.NotFoundException;
import roomescape.service.TimeService;

@RestController
public class TimeController {


    private final TimeService timeservice;

    @Autowired
    public TimeController(TimeService timeservice){
        this.timeservice = timeservice;
    }

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody TimeAddRequest time){
        if(time.getTime().isEmpty()||time.getTime()==null){
            return ResponseEntity.status(ResponseInfo.BAD_REQUEST.getStatus()).body(null);
        }

        Time newTime = timeservice.save(time);

        return ResponseEntity
                .status(ResponseInfo.CREATED.getStatus())
                .location(java.net.URI.create("/times/"+newTime.getId()))
                .body(newTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes(){

        List<Time> times = timeservice.findAllTimes();

        return ResponseEntity.ok().body(times);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {

        timeservice.delete(id);

        return ResponseEntity.status(ResponseInfo.NOT_FOUND.getStatus()).body(null);
    }
}
