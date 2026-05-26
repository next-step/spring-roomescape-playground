package roomescape.timeslot.service;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import roomescape.timeslot.dto.request.TimeslotRequest;
import roomescape.timeslot.dto.response.TimeslotResponse;
import roomescape.timeslot.exception.TimeslotException;
import roomescape.timeslot.model.Timeslot;
import roomescape.timeslot.repository.TimeslotRespository;

import java.util.List;

@Service
public class TimeslotService {

    private final TimeslotRespository timeslotRespository;

    public TimeslotService(TimeslotRespository timeslotRespository) {
        this.timeslotRespository = timeslotRespository;
    }

    public TimeslotResponse addTimeslot(TimeslotRequest request) {
        Timeslot timeslot = new Timeslot(
                null,
                request.timeslot()
        );

        Long newTimeslotId = timeslotRespository.addTimeslot(timeslot);

        Timeslot addedTimeslot = new Timeslot(
                newTimeslotId,
                timeslot.getTimeslot()
        );

        return convertIntoTimeslotDTO(addedTimeslot);
    }

    public List<TimeslotResponse> getAllTimeslots() {
        List<Timeslot> timeslots = timeslotRespository.getAllTimeslots();

        return timeslots.stream()
                .map(this::convertIntoTimeslotDTO)
                .toList();
    }

    public TimeslotResponse getTimeslotById(Long timeId) {
        Timeslot timeslot = timeslotRespository.getTimeslotById(id);
        return convertIntoTimeslotDTO(timeslot);
    }

    public void deleteTimeslotById(Long id) {
        int deletedRowCount = timeslotRespository.deleteTimeslotById(id);
        if (deletedRowCount == 0) {
            throw new TimeslotException("존재하지 않는 시간대에요.");
        }
    }

    public TimeslotResponse convertIntoTimeslotDTO(Timeslot timeslot) {
        return new TimeslotResponse(timeslot.getId(), timeslot.getTimeslot());
    }
}
