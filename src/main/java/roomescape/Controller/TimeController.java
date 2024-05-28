package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Model.*;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private TimeService timeService;

    public TimeController(TimeService timeService){
        this.timeService=timeService;
    }

    @GetMapping("/time")
    public String timePage(){
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getAllTimeList(){
        return ResponseEntity.ok(timeService.getTimeList());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> addTime(@RequestBody RequestTimeDTO requestTimeDTO){
        ReservationTime saveTime=timeService.saveTime(requestTimeDTO);
        URI location=URI.create("/times/"+saveTime.getId());
        return ResponseEntity.created(location).body(saveTime);
    }

    @GetMapping("/times/{id}")
    public ResponseEntity<ReservationTime> getTime(@PathVariable Long id){
        ReservationTime findTime=timeService.viewTime(id);
        return ResponseEntity.ok(findTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<String> deleteTime(@PathVariable Long id){
        timeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
