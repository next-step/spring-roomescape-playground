package roomescape.web.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Dto.reservationDto.RequestDto;
import roomescape.domain.Dto.reservationDto.ResponseDto;
import roomescape.domain.Model.Reservation;
import roomescape.domain.service.ReservationService;


import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping ("/reservations")
    @ResponseBody
    public ResponseEntity<List<ResponseDto>> checkReservation(){
        List<Reservation> reservations = reservationService.findAll();
        List<ResponseDto> responseDtos = reservations.stream()
                .map(ResponseDto::makeResponse)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }
    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ResponseDto> addReservation(@RequestBody RequestDto requestDto){

        Reservation reservation = reservationService.save(requestDto);
        ResponseDto responseDto = ResponseDto.makeResponse(reservation);
        URI location = URI.create("/reservations/"+responseDto.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
