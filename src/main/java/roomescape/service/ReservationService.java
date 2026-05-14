package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationGetResponse;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
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
            throw new NotFoundReservationException();   // ← 여기서 던짐
        }
    }
}
