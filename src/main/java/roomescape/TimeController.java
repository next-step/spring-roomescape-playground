package roomescape;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class TimeController {
    private TimeQueryingDAO queryingDAO;

    public TimeController(TimeQueryingDAO queryingDAO) {
        this.queryingDAO = queryingDAO;
    }
    @GetMapping("/time")
    public void time () {

    }
    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        return ResponseEntity.ok(queryingDAO.getTimes());
    }

    @PostMapping("/times")
    public ResponseEntity<ResponseDto> create (@RequestBody TimeRequestDto request) {

        String time = request.getTime();
        long id = queryingDAO.createTime(time);

        URI location = URI.create("/times/" + id);
        Time newTime = new Time(id, time);

        ResponseDto response = new ResponseDto(HttpStatus.CREATED.value(), "시간이 성공적으로 추가되었습니다.", newTime);
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        int rowsAffected = queryingDAO.deleteTimeById(id);

        if(rowsAffected > 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            throw new NotFoundReservationException("Time with id " + id + " not found." );
        }
    }
}


