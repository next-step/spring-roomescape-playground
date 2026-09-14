package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.ReservationTimeInUseException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTimeService(ReservationTimeRepository reservationTimeRepository,
                                  ReservationRepository reservationRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationTime createReservationTime(LocalTime time) {
        ReservationTime reservationTime = ReservationTime.createNewReservationTime(time);
        return reservationTimeRepository.save(reservationTime);
    }

    public List<ReservationTime> findAllReservationTimes() {
        return reservationTimeRepository.findAll();
    }

    public void deleteReservationTime(Long id) {
        if (reservationRepository.existsByTimeId(id)) {
            throw new ReservationTimeInUseException("예약에서 사용 중인 시간은 삭제할 수 없습니다.");
        }

        int deletedRows;
        try {
            deletedRows = reservationTimeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            if (reservationRepository.existsByTimeId(id)) {
                throw new ReservationTimeInUseException("예약에서 사용 중인 시간은 삭제할 수 없습니다.", e);
            }
            throw e;
        }
        if (deletedRows == 0) {
            throw new ReservationNotFoundException("id " + id + "에 해당하는 시간을 찾을 수 없습니다.");
        }
    }
}
