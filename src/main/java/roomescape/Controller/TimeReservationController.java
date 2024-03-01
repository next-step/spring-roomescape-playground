package roomescape.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.Domain.Time;
import roomescape.Repository.TimeDAO;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/times")
public class TimeReservationController {
    private final TimeDAO timeDAO;

    public TimeReservationController(TimeDAO timeDAO)
    {
        this.timeDAO = timeDAO;
    }

    @GetMapping
    public ResponseEntity<List<Time>> getAllTimeReservations() {
        List<Time> times = timeDAO.findAllTimeReservation();
        return ResponseEntity.ok().body(times);
    }

    /*
    @GetMapping("/{id}")
    public Time getTimeReservation(@PathVariable Long id) {
        return timeDAO.findTimeReservation(id);
    }
    */

    @PostMapping
    public ResponseEntity<Time> createTimeReservation(@RequestBody Time time)
    {
        Long id = timeDAO.createTimeReservation(time);
        Time newTimeReservation = timeDAO.findTimeReservation(id);

        return ResponseEntity.created(URI.create("/times/" + newTimeReservation.getId())).body(newTimeReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeReservation(@PathVariable Long id)
    {
        timeDAO.deleteTimeReservation(id);
        return ResponseEntity.noContent().build();
    }
}
