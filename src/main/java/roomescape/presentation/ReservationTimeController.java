package roomescape.presentation;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dao.ReservationTimeDao;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao repository;

    public ReservationTimeController(final ReservationTimeDao repository) {
        this.repository = repository;
    }

    @GetMapping
    @ResponseBody
    public List<ReservationTimeResponse> getReservationTimes() {
        return repository.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> saveReservationTime(
            @RequestBody ReservationTimeRequest request) {
        final ReservationTime savedTime = repository.save(request.toTime());
        final ReservationTimeResponse response = ReservationTimeResponse.from(savedTime);
        return ResponseEntity
                .created(URI.create("/times/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTimes(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
