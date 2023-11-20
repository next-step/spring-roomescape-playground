package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationRequest;
import roomescape.reservation.dto.response.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public ReservationResponse.createReservationDto saveReservation(ReservationRequest.CreateReservationDto request) {
        if (request.name() == null || request.date() == null || request.time() == null) {
            System.out.println("hello");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "양식을 채워주세요.");
        }

        Reservation reservation = request.toEntity();
        reservationRepository.save(reservation);
        return new ReservationResponse.createReservationDto(reservation);
    }

    @Transactional
    public void removeReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        reservationRepository.delete(reservation);
    }
}
