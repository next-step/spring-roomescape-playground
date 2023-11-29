package roomescape.service;

import static roomescape.exception.ExceptionMessage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.exception.BaseException;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<ReservationResponse> getReservations() {
        return reservations.stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.from(index.incrementAndGet(), reservationRequest);
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation deleteReservation = null;
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                deleteReservation = reservation;
            }
        }
        deleteReservation = Optional.ofNullable(deleteReservation)
            .orElseThrow(() -> new BaseException(NOT_EXIST_RESERVATION));

        reservations.remove(deleteReservation);
    }
}
