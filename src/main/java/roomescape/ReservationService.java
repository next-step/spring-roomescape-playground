package roomescape;

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

        return reservationRepository.save(request);
    }

    public Reservation update(Long id, ReservationRequest request) {
        return reservationRepository.update(id, request);
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
