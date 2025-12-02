package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationAddReq;
import roomescape.dto.ReservationReq;
import roomescape.exception.InvalidRequestReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.service.ReservationService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation")
    public String reservation(Model model){

        //model.addAttribute(reservations1);
        return "new-reservation";
    }

    //예약 조회
    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationReq> reservations(){
        return reservationService.getAllReservations();
    }

    //예약 추가
    @PostMapping("/reservations")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationReq addReservation(@RequestBody ReservationAddReq reservation, HttpServletResponse response){
        ReservationReq newReservation = reservationService.addReservation(reservation);
        response.setHeader("Location", "/reservations/" + newReservation.getId());
        return newReservation;
    }

    //예약 삭제
    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
    }

}