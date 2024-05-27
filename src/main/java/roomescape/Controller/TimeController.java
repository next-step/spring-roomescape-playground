package roomescape.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.DTO.SaveTimeRequest;
import roomescape.DTO.TimeResponse;
import roomescape.Model.JdbcTimeRepository;
import roomescape.Model.Time;

import java.net.URI;

@Controller
public class TimeController {

    @Autowired
    private final JdbcTimeRepository jdbcTimeRepository;

    public TimeController(JdbcTimeRepository jdbcTimeRepository) {
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> save(@RequestBody SaveTimeRequest request) {
        Time time = jdbcTimeRepository.save(request.toTime());
        TimeResponse response = TimeResponse.from(time);
        String uri = "/times/" + time.getId();
        return ResponseEntity.created(URI.create(uri)).body(response);
    }

}
