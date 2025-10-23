package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationAddReq;
import roomescape.dto.ReservationReq;
import roomescape.exception.InvalidRequestReservationException;
import roomescape.exception.NotFoundReservationException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    //AtomicLong: Long 자료형 가지는 Wrapping 클래스. incrementAndGet(): ++x
    private AtomicLong index = new AtomicLong(0);
    private List<ReservationReq> reservations = new ArrayList<>();
    
    @GetMapping("/reservation")
    public String reservation(Model model){

        model.addAttribute(reservations);
        return "reservation";
    }

    //예약 조회
    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationReq> reservations(){

        return reservations;
    }

    //예약 추가
    @PostMapping("/reservations")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationReq addReservation(@RequestBody ReservationAddReq reservation, HttpServletResponse response){
        if(reservation.getName().isEmpty()||reservation.getDate().isEmpty()||reservation.getTime().isEmpty()){
            throw new InvalidRequestReservationException("필요한 인자가 없습니다.");
        }
        ReservationReq newReservation =new ReservationReq(index.incrementAndGet(),reservation.getName(),reservation.getDate(),reservation.getTime());
        reservations.add(newReservation);

        // ResponseEntity 사용하면 header 명시적으로 지정하지 않아도 된다고 한다.
        response.setHeader("Location", "/reservations/" + newReservation.getId());
        return newReservation;
    }

    //예약 삭제
    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id){
        ReservationReq delete=reservations.stream().filter(ReservationReq -> id.equals(ReservationReq.getId())).findAny().orElse(null);
        if(delete==null){
            throw new NotFoundReservationException("삭제할 예약이 없습니다");
        }
        reservations.remove(delete);
    }


}
