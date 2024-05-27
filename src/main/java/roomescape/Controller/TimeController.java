package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.DTO.SaveTimeRequest;
import roomescape.DTO.TimeResponse;
import roomescape.Model.JdbcTimeRepository;
import roomescape.Model.Time;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {

    @Autowired
    private final JdbcTimeRepository jdbcTimeRepository;

    public TimeController(JdbcTimeRepository jdbcTimeRepository) {
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    // time 생성 api
    @PostMapping("/times")
    public ResponseEntity<TimeResponse> save(@RequestBody SaveTimeRequest request) {
        Time time = jdbcTimeRepository.save(request.toTime());
        TimeResponse response = TimeResponse.from(time);
        String uri = "/times/" + time.getId();
        return ResponseEntity.created(URI.create(uri)).body(response);
    }

    // time 조회 api
    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> read() {
        List<Time> times = jdbcTimeRepository.findAll();
        List<TimeResponse> response = times.stream().map(TimeResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    //time 삭제 api
    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcTimeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
