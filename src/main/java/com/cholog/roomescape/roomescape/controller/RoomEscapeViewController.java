package com.cholog.roomescape.roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.cholog.roomescape.roomescape.dto.request.ReservationRequest;
import com.cholog.roomescape.roomescape.dto.response.ReservationResponse;
import com.cholog.roomescape.roomescape.entity.Reservation;
import com.cholog.roomescape.roomescape.service.ReservationService;

import java.net.URI;
import java.util.List;

@Controller
public class RoomEscapeViewController {

    private final ReservationService reservationService;

    public RoomEscapeViewController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

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
}
