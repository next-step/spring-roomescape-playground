package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationGetResponse;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao repository;

    public ReservationService(ReservationDao repository) {
        this.repository = repository;
    }


    public List<ReservationGetResponse> getReservations() {
        List<Reservation> reservations;
        List<ReservationGetResponse> response;

        reservations = repository.findAll();

        response = reservations.stream()
                .map(it -> new ReservationGetResponse(
                        it.getId(),
                        it.getName(),
                        it.getDate(),
                        it.getTime()))
                .toList();

        return response;
    }

    public ReservationCreateResponse addReservation(ReservationCreateRequest reservationCreateRequest) {
        boolean exists = repository.existsByDateAndTime(reservationCreateRequest.getDate(), reservationCreateRequest.getTime());
        if (exists) {
            throw new DuplicateReservationException("이미 예약된 시간입니다.");
        }

        Reservation newReservation = new Reservation(
                null,
                reservationCreateRequest.getName(),
                reservationCreateRequest.getDate(),
                reservationCreateRequest.getTime());

        newReservation = repository.save(newReservation);

        return new ReservationCreateResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime());
    }

    public void deleteReservation(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundReservationException();
        }
    }
}
