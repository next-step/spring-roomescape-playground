package roomescape.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Time;
import roomescape.repository.TimeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeService {
    private final TimeRepository timeRepository;
    public ResponseEntity<List<Time>> findTime() {
        List<Time> times = timeRepository.findAll();
        return ResponseEntity.ok().body(times);
    }
}
