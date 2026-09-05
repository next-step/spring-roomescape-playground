package roomescape;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation create(ReservationRequest request) {
        return reservationRepository.save(request);
    }

    public void delete(Long id) {
        int deletedCount = reservationRepository.delete(id);

        if (deletedCount == 0) {
            throw new NotFoundReservationException();
        }
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(NotFoundReservationException::new);
    }

    public Reservation update(Long id, ReservationRequest request) {
        int updatedCount = reservationRepository.update(id, request);

        if (updatedCount == 0) {
            throw new NotFoundReservationException();
        }

        return findById(id);
    }
}
