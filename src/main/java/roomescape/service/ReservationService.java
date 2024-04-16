package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationResponse> reservations() {
        return reservationRepository.findAll().stream()
            .map(ReservationResponse::new)
            .toList();
    }

    @Transactional
    public ReservationResponse addReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = reservationRepository.findById(
            reservationRepository.save(
                new Reservation(null, request.getName(), request.getDate(), new Time(request.getTime(), null))
            )
        );
        return new ReservationResponse(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
