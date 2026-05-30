package roomescape.timeslot.service;

import org.springframework.stereotype.Service;
import roomescape.timeslot.dto.request.TimeslotRequest;
import roomescape.timeslot.dto.response.TimeslotResponse;
import roomescape.timeslot.exception.TimeslotException;
import roomescape.timeslot.model.Timeslot;
import roomescape.timeslot.repository.TimeslotRepository;

import java.util.List;

@Service
public class TimeslotService {

    private final TimeslotRepository timeslotRepository;

    public TimeslotService(TimeslotRepository timeslotRepository) {
        this.timeslotRepository = timeslotRepository;
    }

    public TimeslotResponse addTimeslot(TimeslotRequest request) {
        Timeslot timeslot = new Timeslot(
                null,
                request.timeslot()
        );

        Long newTimeslotId = timeslotRepository.addTimeslot(timeslot);

        Timeslot addedTimeslot = new Timeslot(
                newTimeslotId,
                timeslot.getTimeslot()
        );

        return convertIntoTimeslotDTO(addedTimeslot);
    }

    public List<TimeslotResponse> getAllTimeslots() {
        List<Timeslot> timeslots = timeslotRepository.getAllTimeslots();

        return timeslots.stream()
                .map(this::convertIntoTimeslotDTO)
                .toList();
    }

    public TimeslotResponse getTimeslotById(Long timeId) {
        Timeslot timeslot = timeslotRepository.getTimeslotById(timeId);
        return convertIntoTimeslotDTO(timeslot);
    }

    public Timeslot getTimeslotObjectById(Long timeId) {
        return timeslotRepository.getTimeslotById(timeId);
    }

    public void deleteTimeslotById(Long id) {
        int deletedRowCount = timeslotRepository.deleteTimeslotById(id);
        if (deletedRowCount == 0) {
            throw new TimeslotException("존재하지 않는 시간대에요.");
        }
    }

    private TimeslotResponse convertIntoTimeslotDTO(Timeslot timeslot) {
        return new TimeslotResponse(timeslot.getId(), timeslot.getTimeslot());
    }
}
