package roomescape.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.exception.DuplicateReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.JdbcReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final JdbcReservationRepository jdbcReservationRepository;

    public ReservationService(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return jdbcReservationRepository.findAll();
    }

    @Transactional
    public Reservation save(Reservation reservation) {
        try {
            return jdbcReservationRepository.save(reservation);
        } catch (DuplicateKeyException e) {
            throw new DuplicateReservationException(
                    "이미 예약된 시간입니다. date=" + reservation.getDate() + ", time=" + reservation.getTime());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        int deleted = jdbcReservationRepository.deleteById(id);
        if (deleted == 0) {
            throw new NotFoundReservationException("예약을 찾을 수 없습니다. id=" + id);
        }
    }
}
