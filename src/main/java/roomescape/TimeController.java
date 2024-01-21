package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {

    @Autowired
    TimeUpdatingDAO timeUpdatingDAO;

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

}
