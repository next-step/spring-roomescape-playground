package roomescape.Service;

import roomescape.DTO.TimeRequestDTO;
import roomescape.DTO.TimeResponseDTO;

import java.util.List;

public interface TimeService {
    public List<TimeResponseDTO> findAllTimeReservations();

    public TimeResponseDTO findTimeReservationById(Long id);

    public Long createTimeReservation(TimeRequestDTO timeRequest);

    public void deleteTimeReservationById(Long id);
}
