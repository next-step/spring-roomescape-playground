package roomescape.service;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
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

    public Reservation insertNewReservation(Reservation reservation) {
        if(Reservation.checkValidity(reservation)) throw new NoParameterException();

        Time timeWithValue = timeDAO.findSpecificTime(reservation.getTime().getId());
        reservation = new Reservation(
                reservation.getName(),
                reservation.getDate(),
                timeWithValue
        );

        Long id = reservationDAO.insertNewReservation(reservation);
        return Reservation.toEntity(reservation, id);
    }

    public List<Reservation> findAllReservations() { return reservationDAO.findAllReservations(); }

    public void updateReservation(Reservation newReservation, Long id) {
        Reservation reservation = reservationDAO.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDAO.updateReservation(reservation, reservation.getId());
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationDAO.findAllReservations().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(NotFoundReservationException::new);

        reservationDAO.deleteReservation(id);
    }
}
