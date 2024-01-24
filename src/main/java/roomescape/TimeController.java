package roomescape;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TimeController {
    @Autowired
    private TimeDAO timeDAO;

    public TimeController() {
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        List<Time> timeList = timeDAO.findAllTimes();
        return ResponseEntity.ok().body(timeList);
    }

    @PostMapping("/times")
    public ResponseEntity<Time> create(@RequestBody Time time) {
        if(Time.checkValidity(time)) throw new NoParameterException();

        Long id = timeDAO.insertNewTime(time);
        Time newTime = Time.toEntity(time, id);
        return ResponseEntity.created(URI.create("/times/" + id)).body(newTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Time time = timeDAO.findAllTimes().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundTimeException::new);

        timeDAO.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
