package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.reservation.request.ReservationRequest;
import roomescape.dto.reservation.response.ReservationResponse;
import roomescape.dto.time.response.TimeResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.repository.ReservationDAO;
import roomescape.repository.TimeDAO;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationService(ReservationDAO reservationDAO, TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public List<ReservationResponse> showReservations() {
        List<Reservation> reservations = reservationDAO.findReservations();
        return reservations.stream()
            .map(reservation -> new ReservationResponse(reservation.getId(), reservation.getName(),
                reservation.getDate(), new TimeResponse(reservation.getTime().getId(), reservation.getTime().getTime())))
            .toList();
    }

    public ReservationResponse reserve(ReservationRequest request) {
        Time time = timeDAO.findTime(request.time());
        validateDateTime(request.date(), time.getTime());
        Reservation reservation = new Reservation(request.name(), request.date(), time);
        Reservation response = reservationDAO.createReservation(reservation);
        return new ReservationResponse(response.getId(), response.getName(), response.getDate(), new TimeResponse(
            reservation.getTime().getId(), reservation.getTime().getTime()));
    }

    public void cancelReservation(Long reservationId) {
        reservationDAO.deleteReservation(reservationId);
    }

    private void validateDateTime(LocalDate reservationDate, LocalTime reservationTime) {
        if (reservationDate == null || reservationTime == null) {
            throw new InvalidValueException(ErrorMessage.INVALID_DATE_TIME.getMessage());
        }

        LocalDateTime reservationDateTime = LocalDateTime.of(reservationDate, reservationTime);

        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidValueException(ErrorMessage.INVALID_FUTURE_TIME.getMessage());
        }
    }
}
