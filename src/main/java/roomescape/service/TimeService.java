package roomescape.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;
import roomescape.dto.TimeDto;
import roomescape.exception.CustomException;

import java.net.URI;
import java.util.List;

@Service
public class TimeService {

    private TimeDAO timeDAO;
    public TimeService(TimeDAO timeDAO){
        this.timeDAO = timeDAO;
    }
    public List<TimeDto> getTimes() {

        return timeDAO.getTimes();
    }

    public ResponseEntity<TimeDto> postime(TimeDto timeDto) {
        int id = timeDAO.postTime(timeDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        URI uri = URI.create("/times/" + id);
        httpHeaders.setLocation(uri);

        Time time = timeDAO.findTimeById(id);
        TimeDto response = TimeDto.builder()
                .id(time.getId())
                .time(time.getTime()).build();

        return new ResponseEntity<TimeDto>(response, httpHeaders, HttpStatus.CREATED);
    }

    public void deleteTime(Long id) {
        try {
            timeDAO.findTimeById(id);
            timeDAO.deleteTime(id);
        }catch(Exception e){
            throw new CustomException();
        }
    }
}
