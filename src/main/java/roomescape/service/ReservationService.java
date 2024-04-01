package roomescape.service;

import static roomescape.exception.ExceptionMessage.*;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.exception.BaseException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Long id = reservationRepository.create(reservationRequest);
        Reservation reservation = reservationRepository.findById(id);
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        if (reservationRepository.findById(id) == null) {
            throw new BaseException(NOT_EXIST_RESERVATION);
        }
        reservationRepository.delete(id);
    }
}