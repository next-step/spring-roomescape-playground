package roomescape.time;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TimeService {
	private final TimeDAO timeDAO;

	public TimeService(TimeDAO timeDAO) {
		this.timeDAO = timeDAO;
	}

	public TimeResponse.Create create(TimeRequest.Create request) {
		Time savedTime = timeDAO.save(request.toEntity());
		return TimeResponse.Create.toDTO(savedTime);
	}

	public TimeResponse.Read getTimeById(Long id) {
		Time time = timeDAO.findById(id);
		return TimeResponse.Read.toDTO(time);
	}

	public List<TimeResponse.Read> getTimes() {
		List<Time> all = timeDAO.findAll();
		return TimeResponse.Read.toDTO(all);
	}

	public void deleteTimeById(Long id) {
		timeDAO.deleteById(id);
	}

}
