package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.controller.dto.ReservationSaveRequestDto;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;

import java.util.List;

@Transactional
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeService = reservationTimeService;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void deleteById(long deleteId) {
        reservationRepository.deleteById(deleteId);
    }

    public long save(ReservationSaveRequestDto request) {
        ReservationTime time = reservationTimeService.findById(request.timeId());
        Reservation reservation = new Reservation(request.name(), request.date(), time);

        return reservationRepository.save(reservation);
    }
}
