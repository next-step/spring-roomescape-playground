package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roomescape.controller.dto.ReservationCreate;
import roomescape.controller.dto.ReservationResponse;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.repository.ReservationRepository;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeDao timeDao;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TimeDao timeDao) {
        this.reservationRepository = reservationRepository;
        this.timeDao = timeDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @Override
    public ReservationResponse add(ReservationCreate request) {
        Time time = timeDao.findById(request.time());
        Reservation reservation = new Reservation(null, request.name(), request.date(), time);
        return ReservationResponse.from(reservationRepository.save(reservation));
    }

    @Override
    public void remove(Long id) throws NoSuchElementException {
        if (!reservationRepository.existsById(id)) {
            throw new NoSuchElementException("존재하지 않는 예약입니다.");
        }

        reservationRepository.deleteById(id);
    }
}
