package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findReservations() {
        return reservations;
    }

    public ReservationResponse.createReservationDto saveReservation(ReservationRequest.CreateReservationDto request) {
        Reservation reservation = request.toEntity();
        reservationRepository.save(reservation);
        return new ReservationResponse.createReservationDto(reservation);
    }
}
