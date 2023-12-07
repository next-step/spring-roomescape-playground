package roomescape.time;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TimeService {
	private final TimeDAO timeDAO;

	public TimeService(TimeDAO timeDAO) {
		this.timeDAO = timeDAO;
	}

	@Transactional
	public TimeResponse.Create create(TimeRequest.Create request) {
		Time savedTime = timeDAO.save(request.toEntity());
		return TimeResponse.Create.fromEntity(savedTime);
	}

	public TimeResponse.Read getTimeById(Long id) {
		Time time = timeDAO.findById(id);
		return TimeResponse.Read.fromEntity(time);
	}

	public List<TimeResponse.Read> getTimes() {
		List<Time> all = timeDAO.findAll();
		return TimeResponse.Read.fromEntity(all);
	}

	@Transactional
	public void deleteTimeById(Long id) {
		timeDAO.deleteById(id);
	}

}
