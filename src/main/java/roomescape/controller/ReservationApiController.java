package roomescape.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.BadRequestException;
import roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> reservations(){
        List<ReservationResponse> responseDtoList = reservationService.findAllReservations();

        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest reservationRequest){
        try {
            ReservationResponse responseDto = reservationService.addReservation(reservationRequest);
            return ResponseEntity.created(URI.create("/reservations/" + responseDto.getId()))
                    .body(responseDto);
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("예약 추가 중 오류가 발생했습니다.", e);
        }
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancleReservation(@PathVariable Long id){
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("예약 취소 중 오류가 발생했습니다.", e);
        }
    }
}
