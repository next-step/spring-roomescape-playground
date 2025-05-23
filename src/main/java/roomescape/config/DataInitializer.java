package roomescape.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import roomescape.dao.TimeDAO;
import roomescape.domain.Time;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final TimeDAO timeDAO;

    @PostConstruct
    public void init() {
        if (timeDAO.findAll().isEmpty()) {
            timeDAO.save(new Time(null, "10:00"));
            timeDAO.save(new Time(null, "13:00"));
            timeDAO.save(new Time(null, "17:00"));
        }
    }
}
