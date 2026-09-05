package com.cholog.roomescape.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/time")
public class TimeViewController {

    @GetMapping
    public String getTimes() {
        return "time";
    }
}
