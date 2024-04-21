package roomescape.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationVO;
import roomescape.exception.CustomException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationDAO reservationDAO;

    @GetMapping("/reservation")
    public String reservation(){

        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationVO>> getReservations(){
        List<ReservationVO> result = reservationDAO.getReservations();

        return ResponseEntity.ok(result);
    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationVO> postReservation(@RequestBody @Valid ReservationVO reservationVO) {
        int id = reservationDAO.postReservation(reservationVO);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = URI.create("/reservations/" + id);
        httpHeaders.setLocation(uri);
        reservationVO.setIdentifyKey((long) id);

        return new ResponseEntity<ReservationVO>(reservationVO, httpHeaders, HttpStatus.CREATED);
    }

    @ResponseBody
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable(value = "id", required = true) Long id){
        try {
            reservationDAO.getReservationById(id);
            reservationDAO.deleteReservations(id);
        }catch(Exception e){
            throw new CustomException();
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
