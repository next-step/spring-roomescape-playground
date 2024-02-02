package roomescape.presentation.api;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.ReservationService;
import roomescape.application.dto.CreateInfoReservationDto;
import roomescape.application.dto.CreateReservationDto;
import roomescape.application.dto.ReadReservationDto;
import roomescape.presentation.dto.request.CreateReservationRequest;
import roomescape.presentation.dto.response.CreateReservationResponse;
import roomescape.presentation.dto.response.ReadReservationResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReadReservationResponse>> readAll() {
        final List<ReadReservationDto> readReservationDtos = reservationService.readAll();
        final List<ReadReservationResponse> responses = readReservationDtos.stream()
                                                                           .map(ReadReservationResponse::from)
                                                                           .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<CreateReservationResponse> create(@RequestBody @Valid final CreateReservationRequest request) {
        final CreateReservationDto createReservationDto = new CreateReservationDto(
                request.getName(),
                request.getDate(),
                request.getTimeId()
        );
        final CreateInfoReservationDto createInfoReservationDto = reservationService.create(createReservationDto);
        final CreateReservationResponse response = CreateReservationResponse.from(createInfoReservationDto);

        return ResponseEntity.created(URI.create("/reservations/" + createInfoReservationDto.getId()))
                             .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationService.deleteById(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
