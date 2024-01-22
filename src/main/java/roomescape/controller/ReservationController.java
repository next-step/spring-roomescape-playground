package roomescape.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.application.ReservationService;
import roomescape.application.dto.CreateInfoReservationDto;
import roomescape.application.dto.CreateReservationDto;
import roomescape.application.dto.ReadReservationDto;
import roomescape.controller.dto.request.CreateReservationRequest;
import roomescape.controller.dto.response.CreateReservationResponse;
import roomescape.controller.dto.response.ReadReservationResponse;

import java.net.URI;
import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation.html";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReadReservationResponse>> readAll() {
        final List<ReadReservationDto> readReservationDtos = reservationService.readAll();
        final List<ReadReservationResponse> responses = readReservationDtos.stream()
                                                                           .map(ReadReservationResponse::from)
                                                                           .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<CreateReservationResponse> create(@RequestBody @Valid final CreateReservationRequest request) {
        final CreateReservationDto createReservationDto = new CreateReservationDto(
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        final CreateInfoReservationDto createInfoReservationDto = reservationService.create(createReservationDto);
        final CreateReservationResponse response = CreateReservationResponse.from(createInfoReservationDto);

        return ResponseEntity.created(URI.create("/reservations/" + createInfoReservationDto.getId()))
                             .body(response);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        reservationService.deleteById(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
