package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        validateRequest(request);
        Reservation reservation = new Reservation(
                null,
                request.getName(),
                request.getDate(),
                request.getTime()
        );
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public void deleteById(Long id) {
        if (!reservationRepository.deleteById(id)) {
            throw new NotFoundReservationException(id);
        }
    }

    private void validateRequest(ReservationRequest request) {
        if (request.getName() == null || request.getName().isBlank()
                || request.getDate() == null || request.getTime() == null) {
            throw new InvalidReservationRequestException();
        }
    }
}
