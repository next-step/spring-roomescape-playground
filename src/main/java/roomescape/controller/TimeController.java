package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Times;
import roomescape.dto.SaveTimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.TimeRepository;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("/times/home")
    public String gotoTimePage(){
        return "time";
    }

    @GetMapping("/times")
    public ResponseEntity<List<Times>> getTimes(){
        final List<Times> times = timeRepository.findAll();
        return ResponseEntity.ok(times);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> createTime(@RequestBody SaveTimeRequest request){
        // TODO create TimeResponse
        Times times = timeRepository.save(request.toTimes());
        TimeResponse response = TimeResponse.from(times);
        return ResponseEntity.created(URI.create("/times/" + times.getId())).body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<String> deleteTime(@PathVariable int id){
        timeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
