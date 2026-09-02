package roomescape;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(NotFoundReservationException::new);
    }

    public Reservation save(ReservationRequest request) {
        return reservationRepository.save(request);
    }

    public Reservation update(Long id, ReservationRequest request) {
        return reservationRepository.update(id, request);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
