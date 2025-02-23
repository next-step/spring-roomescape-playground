package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.error.ErrorMessage;
import roomescape.error.exception.InvalidValueException;
import roomescape.repository.ReservationDAO;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> showReservations() {
        return reservationDAO.findReservations();
    }

    public ReservationResponse reserve(ReservationCreateRequest request) {
        validateDate(request.date());
        validateTime(request.date(), request.time());
        return reservationDAO.createReservation(request);
    }

    public void cancelReservation(Long reservationId) {
        reservationDAO.deleteReservation(reservationId);
    }

    private void validateDate(LocalDate reservationDate) {
        if (reservationDate == null) {
            throw new InvalidValueException(ErrorMessage.INVALID_DATE.getMessage());
        }
        if (reservationDate.isBefore(LocalDate.now())) {
            throw new InvalidValueException(ErrorMessage.INVALID_FUTURE_TIME.getMessage());
        }
    }

    private void validateTime(LocalDate reservationDate, LocalTime reservationTime) {
        if (reservationTime == null) {
            throw new InvalidValueException(ErrorMessage.INVALID_TIME.getMessage());
        }
        if (reservationDate.equals(LocalDate.now()) && reservationTime.isBefore(LocalTime.now())) {
            throw new InvalidValueException(ErrorMessage.INVALID_FUTURE_TIME.getMessage());
        }
    }
}
