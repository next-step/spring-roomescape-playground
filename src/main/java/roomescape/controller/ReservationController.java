package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationDto;
import roomescape.exception.CustomException;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {
    private final AtomicLong index = new AtomicLong(0);
    private final List<ReservationDto> list = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservations(){
        return ResponseEntity.ok(list);

    }

    @ResponseBody
    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> postReservation(@RequestBody ReservationDto reservationDto) {
        if(reservationDto.getDate() == null || reservationDto.getTime() == null|| reservationDto.getName().isBlank()){
            throw new CustomException();
        }

        reservationDto.setIdentifyKey(index.incrementAndGet());
        list.add(reservationDto);
        HttpHeaders httpHeaders = new HttpHeaders();


        URI uri = URI.create("/reservations/" + reservationDto.getId());

        // 기본 URI 설정
        httpHeaders.setLocation(uri);
        return new ResponseEntity<ReservationDto>(reservationDto, httpHeaders, HttpStatus.CREATED);

    }

    @ResponseBody
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable(value = "id") Long id){
        if(id == null){
            throw new CustomException();
        }
        if(!list.removeIf(reservationDto -> Objects.equals(reservationDto.getId(), id))){
            throw new CustomException();
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }




}
