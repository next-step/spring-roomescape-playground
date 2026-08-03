package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class RoomEscapeController {

    private Logger logger = LoggerFactory.getLogger(RoomEscapeController.class);

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/")
    public String home(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation(
    ) {
        return "reservation";
    }

    @ResponseBody
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        return ResponseEntity.ok(this.reservations);
    }

    @PostMapping("/reservations")
    public String postReservation(
            @RequestBody ReservationRequest request,
            Model model,
            HttpServletResponse response
    ) {

        Reservation reservation = new Reservation(
                request.name(),
                request.date(),
                request.time()
        );

        reservations.add(Reservation.toEntity(
                index.incrementAndGet(),
                reservation
        ));

        model.addAttribute("reservations", reservation);

       response.setStatus(HttpServletResponse.SC_CREATED);

       /*
       logger.info("id: {}", reservation.getId());
       logger.info("name: {}", reservation.getName());
       logger.info("date: {}", reservation.getDate());
       logger.info("time: {}", reservation.getTime());
       logger.info("{} rows created", reservations.size());
        */
       return "redirect:/reservations";
    }
}
