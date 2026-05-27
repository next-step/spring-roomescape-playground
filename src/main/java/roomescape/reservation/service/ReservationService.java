package roomescape.reservation.service;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import roomescape.global.controller.InternalErrorException;
import roomescape.reservation.ReservationDoesNotExistException;
import roomescape.reservation.ReservationDuplicateTimeException;
import roomescape.reservation.ReservationInputFormatException;
import roomescape.reservation.domain.*;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.domain.Times;

import java.util.List;

@Service
public class ReservationService {
    private final Reservations reservations;
    private final Times times;

    public ReservationService(Reservations reservations, Times times) {
        this.reservations = reservations;
        this.times = times;
    }

    public List<ReservationResponse> getReservations() {
        return this.reservations.getAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(@Nonnull CreateReservationRequest request) {
        CreateReservationInfo info;

        try {
            info = request.convertToDomain(times);
        } catch (ReservationException.InputFormat e) {
            throw new ReservationInputFormatException(e.getField(), e.getMessage());
        }

        try {
            Reservation reservation = reservations.create(info);
            return ReservationResponse.from(reservation);
        } catch (ReservationException.DuplicateDateTime e) {
            throw new ReservationDuplicateTimeException(e.previous);
        } catch (ReservationException e) {
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
