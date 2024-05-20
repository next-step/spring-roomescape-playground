package roomescape.web.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Dto.RequestDto;
import roomescape.domain.Dto.ResponseDto;
import roomescape.domain.Model.Reservation;
import roomescape.domain.Repository.BasicRepository;


import java.lang.reflect.Member;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
public class ReservationController {
    private final BasicRepository basicRepository;

    public ReservationController(@Qualifier("jdbcReservationRepository") BasicRepository basicRepository) {
        this.basicRepository = basicRepository;
    }

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    @GetMapping ("/reservations")
    @ResponseBody
    public ResponseEntity<List<ResponseDto>> checkReservation(){
        List<Reservation> reservations = basicRepository.findAll();
        List<ResponseDto> responseDtos = reservations.stream()
                .map(ResponseDto::makeResponse)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }
    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<ResponseDto> addReservation(@RequestBody RequestDto requestDto){

        Reservation reservation = basicRepository.save(requestDto.toReservation());
        ResponseDto responseDto = ResponseDto.makeResponse(reservation);
        URI location = URI.create("/reservations/"+responseDto.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        basicRepository.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

}
