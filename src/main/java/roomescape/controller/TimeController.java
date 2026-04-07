package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.reservationDto.ReservationResponse;
import roomescape.dto.timeDto.TimeCreateRequest;
import roomescape.dto.timeDto.TimeResponse;
import roomescape.model.Time;
import roomescape.service.TimeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimeController {
    private final TimeService service;

    @GetMapping
    public List<TimeResponse> getAllReservations() {
        return service.getAllTime()
                .stream()
                .map(TimeResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        service.deleteTime(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TimeResponse> createReservation(@Valid @RequestBody TimeCreateRequest request) {
        Time savedTime = service.addTime(request.toEntity());

        TimeResponse timeResponse = TimeResponse.from(savedTime);
        URI location = URI.create("/times/" + savedTime.getId());

        return ResponseEntity.created(location).body(timeResponse);
    }
}
