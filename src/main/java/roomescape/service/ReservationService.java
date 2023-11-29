package roomescape.service;

import static roomescape.exception.ExceptionMessage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.exception.BaseException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.getReservations().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.of(index.incrementAndGet(), reservationRequest.getName(),
            reservationRequest.getDate(), reservationRequest.getTime());
        reservations.add(reservation);
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation deleteReservation = reservations.stream()
            .filter(reservation -> Objects.equals(reservation.getId(), id))
            .findFirst()
            .orElseThrow(() -> new BaseException(NOT_EXIST_RESERVATION));

        reservations.remove(deleteReservation);
    }
}
