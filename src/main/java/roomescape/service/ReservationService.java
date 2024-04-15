package roomescape.service;

import static roomescape.exception.
import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.exception.BaseException;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse getReservation(Long id) {
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Long id = reservationRepository.create(reservationRequest);
        Reservation reservation = reservationRepository.findById(id);
        return ReservationResponse.from(reservation);
    }
    public Long createReservation(ReservationRequest reservationRequest) {
        Time time = timeRepository.findById(reservationRequest.getTime());
        return reservationRepository.create(reservationRequest.getName(), reservationRequest.getDate(), time.getId());
    }

    public void deleteReservation(Long id) {
    public void deleteReservation(Long id) {
        if (reservationRepository.findById(id) == null) {
            throw new BaseException(NOT_EXIST_RESERVATION);
        }
        reservationRepository.delete(id);
    }
}