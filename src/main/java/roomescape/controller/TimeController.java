package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.NotFoundTimeException;
import roomescape.exception.InvalidReservationException;
import roomescape.service.RoomescapeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final RoomescapeService roomescapeService;

    public TimeController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping
    public List<TimeResponse> getTimes() {
        return roomescapeService.findAllTimes();
    }

    @PostMapping
    public ResponseEntity<TimeResponse> addTime(@RequestBody TimeRequest request) {
        TimeResponse response = roomescapeService.saveTime(request);
        return ResponseEntity.created(URI.create("/times/" + response.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        roomescapeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<String> handleNotFoundTimeException(NotFoundTimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleBadRequest(InvalidReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
