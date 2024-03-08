package roomescape.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import roomescape.domain.Time;
import roomescape.dto.time.TimeResponseDTO.AddTimeResponse;
import roomescape.dto.time.TimeResponseDTO.QueryTimeResponse;
import roomescape.repository.TimeRepository;

@Profile("default")
@RequiredArgsConstructor
public class DefaultTimeService implements TimeService {
	private final TimeRepository timeRepository;

	@Override
	public List<QueryTimeResponse> getAllTimes() {
		List<Time> times = timeRepository.findAll();
		return times.stream()
				.map(time -> new QueryTimeResponse(time.id(), time.value()))
				.toList();
	}

	@Override
	public AddTimeResponse addTime(String value) {
		Long id = timeRepository.save(new Time(null, value));
		return new AddTimeResponse(id, value);
	}

	@Override
	public void deleteTime(Long id) {
		timeRepository.deleteById(id);
	}
}
