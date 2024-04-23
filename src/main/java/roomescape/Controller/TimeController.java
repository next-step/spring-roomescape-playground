package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import roomescape.Service.TimeService;
import roomescape.Domain.Time;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/times")
public class TimeController {
    @Autowired
    private TimeService timeService;
    @GetMapping
    public List<Time> getAllTime(){
        return timeService.getAllTime();
    }

    @PostMapping
    public ResponseEntity<Time> createTime(@RequestBody Time time){
        Time newTime = timeService.createdTime(time);
        return ResponseEntity.created(URI.create("/times/" + newTime.getId())).body(newTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        timeService.deletedTime(id);
        return ResponseEntity.noContent().build();
    }
}
