package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import roomescape.dao.TimeQueryingDAO;
import roomescape.dao.TimeUpdatingDAO;
import roomescape.domain.Time;

@RestController
public class TimeController {

    @Autowired
    TimeUpdatingDAO timeUpdatingDAO;

    @Autowired
    TimeQueryingDAO timeQueryingDAO;

    @PostMapping("/times")
    public ResponseEntity<Time> createTime(@RequestBody Time time){
        if(time.getTime().isEmpty()||time.getTime()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Time newTime = new Time();

        newTime.setTime(time.getTime());

        Number newId = timeUpdatingDAO.save(newTime);
        newTime.setId(newId.longValue());

        return ResponseEntity
                .status(201)
                .location(java.net.URI.create("/times/"+newTime.getId()))
                .body(newTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> getTimes(){
        List<Time> times = timeQueryingDAO.getAllTimes();
        return ResponseEntity.ok().body(times);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Time> deleteTime(@PathVariable long id) {

        int row = timeUpdatingDAO.delete(id);

        if(row>0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
