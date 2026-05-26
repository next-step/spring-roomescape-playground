package roomescape.timeslot.service;

import org.springframework.stereotype.Service;
import roomescape.timeslot.dto.request.TimeslotRequest;
import roomescape.timeslot.dto.response.TimeslotResponse;
import roomescape.timeslot.model.Timeslot;
import roomescape.timeslot.repository.TimeslotRespository;

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

    public TimeslotResponse convertIntoTimeslotDTO(Timeslot timeslot) {
        return new TimeslotResponse(timeslot.getId(), timeslot.getTimeslot());
    }
}
