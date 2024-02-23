package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
@Transactional(readOnly = true)
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public Reservation add(Reservation request) {
        return reservationRepository.save(request);
    }

    @Override
    @Transactional
    public void remove(Long id) throws NoSuchElementException {
        if (!reservationRepository.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }

        reservationRepository.deleteById(id);
    }
}
