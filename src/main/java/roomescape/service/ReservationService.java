package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationDomain;
import roomescape.domain.TimeDomain;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;

    }

    public List<ReservationDomain> findAll() {
        return reservationRepository.findAll();
    }

    public ReservationDomain save(final ReservationRequest request) {
        final TimeDomain time = timeRepository.findById(request.time());
        final ReservationDomain reservation = new ReservationDomain(request.name(), request.date(), time);
        return reservationRepository.save(reservation);
    }

    public void deleteById(final Long id) {
        reservationRepository.deleteById(id);
    }
}
