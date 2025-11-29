package roomescape.service_layer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeResponse;
import roomescape.repository_layer.dao.ReservationDao;
import roomescape.web_layer.controller.exception.FailMessage;
import roomescape.web_layer.controller.exception.NotFoundReservationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeService timeService;

    public ReservationResponse registerReservation(ReservationRequest request) {
        Long timeId = Long.parseLong(request.time());
        TimeResponse response = timeService.getTimeById(timeId);
        Time time = Time.of(response.id(), response.time());

        Reservation reservation = Reservation.create(
                null,
                request.name(),
                request.date(),
                time
        );

        Long id = reservationDao.insert(reservation);
        Reservation newReservation = Reservation.of(
                id,
                request.name(),
                request.date(),
                time
        );
        return ReservationResponse.from(newReservation);
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream().map(ReservationResponse::from).toList();
    }


    public void delete(Long id) {
        int result = reservationDao.delete(id);

        if (result == 0) {
            throw new NotFoundReservationException(FailMessage.BAD_REQUEST);
        }
    }
}
