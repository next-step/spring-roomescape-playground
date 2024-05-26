package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DAO.TimeDAO;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.util.List;

@Service
public class TimeService {
    private final TimeDAO timeDAO;

    @Autowired
    public TimeService(DataSource dataSource) {
        this.timeDAO = new TimeDAO(dataSource); // 이 부분 수정
    }

    public List<Time> findAll() {
        return timeDAO.findAll();
    }

    public Time findById(long id) {
        return timeDAO.findById(id);
    }

    public long save(Time time) {
        return timeDAO.save(time);
    }

    public long deleteById(long id) {
        return timeDAO.deleteById(id);
    }
}