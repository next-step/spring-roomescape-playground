package roomescape.reservation;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import roomescape.global.controller.InternalErrorException;
import roomescape.reservation.domain.*;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {
    private final Reservations reservations;

    public ReservationService(Reservations reservations) {
        this.reservations = reservations;
    }

    public List<ReservationResponse> getReservations() {
        return this.reservations.getAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(@Nonnull CreateReservationRequest request) {
        CreateReservationInfo info;

        try {
            info = request.convertToDomain();
        } catch (ReservationException.InputFormat e) {
            throw new ReservationInputFormatException(e.getField(), e.getMessage());
        }

        try {
            Reservation reservation = reservations.create(info);
            return ReservationResponse.from(reservation);
        } catch (ReservationException e) {
            if (e instanceof ReservationException.DuplicateTime)
                throw new ReservationDuplicateTimeException();

            throw new InternalErrorException(e);
        }
    }

    public void deleteReservation(@Nonnull ReservationId id) {
        try {
            reservations.delete(id);
        } catch (ReservationException e) {
            if (e instanceof ReservationException.DoesNotExist)
                throw new ReservationDoesNotExistException();

            throw new InternalErrorException(e);
        }
    }
}
