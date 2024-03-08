package roomescape.service;

import java.util.List;
import roomescape.dto.time.TimeResponseDTO.AddTimeResponse;
import roomescape.dto.time.TimeResponseDTO.QueryTimeResponse;

public interface TimeService {
	List<QueryTimeResponse> getAllTimes();

	AddTimeResponse addTime(String time);

	void deleteTime(Long id);
}
