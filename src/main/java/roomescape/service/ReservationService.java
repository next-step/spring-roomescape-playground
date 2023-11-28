package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.domain.ReservationValidator;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationValidator reservationValidator = new ReservationValidator();

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    public List<ReservationResponse> getReservations() {
        return reservations.stream()
            .map(ReservationResponse::toDto)
            .collect(Collectors.toList());
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.toDomain(index.incrementAndGet(),reservationRequest);
        reservations.add(reservation);
        return ReservationResponse.toDto(reservation);
    }

    public void deleteReservation(Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
