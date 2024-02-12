package roomescape.service;

import java.util.List;
import java.util.Objects;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.exception.NoParameterException;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public ReservationResponse insertNewReservation(ReservationRequest reservationRequest) {
        Time timeWithValue = timeDAO.findSpecificTime(reservationRequest.getTime());
        Reservation reservation = new Reservation(
                reservationRequest.getName(),
                reservationRequest.getDate(),
                timeWithValue
        );

        Long id = reservationDAO.insertNewReservation(reservation);
        reservation = Reservation.toEntity(reservation, id);
        return new ReservationResponse(reservation);
    }

    public List<Reservation> findAllReservations() { return reservationDAO.findAllReservations(); }

    public void updateReservation(ReservationRequest reservationRequest, Long id) {
        Reservation reservation = reservationDAO.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDAO.updateReservation(reservation, reservation.getId());
    }

    public void deleteReservation(Long id) {
        try {
            reservationDAO.deleteReservation(id);
        } catch (DataAccessException e) {
            throw new NotFoundReservationException();
        }
    }
}
