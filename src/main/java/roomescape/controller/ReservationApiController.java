package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequestDto;
import roomescape.dto.response.ReservationResponseDto;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationNotFoundException;
import roomescape.service.ReservationDto;
import roomescape.service.ReservationService;

@RestController // @Controller + @ResponseBody: 특정 클래스가 RESTful 웹 서비스의 컨트롤러 역할을 하도록 지정
public class ReservationApiController {

  private final ReservationService reservationService;

  public ReservationApiController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping("/reservations")
  public ResponseEntity<List<ReservationResponseDto>> readReservation() {
    List<ReservationDto> reservations = reservationService.findAll();
    List<ReservationResponseDto> response =
        reservations.stream()
                    .map(ReservationResponseDto::from)
                    .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/reservations")
  public ResponseEntity<ReservationResponseDto> createReservation(@Valid @RequestBody final ReservationRequestDto reservationRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new IllegalArgumentException(ErrorMessage.INVALID_ARGUMENT.getMessage());
    }

    Reservation reservation = new Reservation(
        null,
        reservationRequest.getName(),
        reservationRequest.getDate(),
        reservationRequest.getTime());

    ReservationDto newReservation = reservationService.save(reservation);

    ReservationResponseDto response = ReservationResponseDto.from(newReservation);

    return ResponseEntity.created(URI.create("/reservations/" + newReservation.getId()))
                         .body(response);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") Long id) {
    try {
      reservationService.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (ReservationNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
