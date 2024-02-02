package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Transactional
@Service
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional(readOnly = true)
    public ReservationTime findById(long id) {
        return reservationTimeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ReservationTime> findAll() {
        return reservationTimeRepository.findAll();
    }

    public long save(ReservationTime reservationTime) {
        return reservationTimeRepository.save(reservationTime);
    }

    public void deleteById(long deleteId) {
        reservationTimeRepository.deleteById(deleteId);
    }
}
