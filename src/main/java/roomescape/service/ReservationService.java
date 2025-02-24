package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        validateDateTime(request.date(), request.time());
        Reservation reservation = new Reservation(request.name(), request.date(), request.time());
        Reservation response = reservationDAO.createReservation(reservation);
        return new ReservationResponse(response.getId(), response.getName(), response.getDate(), response.getTime());
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
