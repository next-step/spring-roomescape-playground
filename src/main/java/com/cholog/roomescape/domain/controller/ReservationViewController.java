package com.cholog.roomescape.domain.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationViewController {

    @GetMapping("/")
    public String home(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return "home";
    }

    @GetMapping("/reservation")
    public String getReservation(
    ) {
        return "new-reservation";
    }
}
