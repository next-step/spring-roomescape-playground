package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TimeController {

    @GetMapping("/time")
    public String createTimeForm() {
        return "time.html";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<?> createTime() {
        return null;
    }

    @GetMapping("/times")
    @ResponseBody
    public List<ResponseEntity<?>> getTimes() {
        return null;
    }

    @DeleteMapping("/times/{deletedTimeId}")
    public HttpStatus deleteTime() {
        return null;
    }
}
