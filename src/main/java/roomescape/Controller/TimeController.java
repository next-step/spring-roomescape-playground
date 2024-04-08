package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.Domain.Time;
import roomescape.Service.TimeService;


import java.util.List;

@RestController
public class TimeController {

    @Autowired
    private TimeService timeService;


    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        ResponseEntity<Time> responseEntity=timeService.addTime(time);
        return ResponseEntity.created(responseEntity.getHeaders().getLocation()).body(responseEntity.getBody());
    }

    @GetMapping("/times")
    public List<Time> getTimes(){ return timeService.getAllTimes();}

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTimes(@PathVariable Long id){
        timeService.deleteTimes(id);
        return ResponseEntity.noContent().build();
    }
}