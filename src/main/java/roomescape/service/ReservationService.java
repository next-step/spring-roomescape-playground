package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.TimeRequest;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public Reservation save(String name, java.time.LocalDate date, Long timeId, Long themeId) {
        if (timeId == null) {
            throw new BadRequestException("유효하지 않은 시간 ID 인프라 요청입니다.");
        }
        if (!timeRepository.existsById(timeId)) {
            throw new BadRequestException("존재하지 않는 시간입니다.");
        }
        if (reservationRepository.countByDateAndTimeId(date, timeId) > 0) {
            throw new BadRequestException("해당 시간대에 이미 예약이 가득 차 있습니다.");
        }

        Long generatedId = reservationRepository.save(name, date, timeId, themeId);

        Time time = timeRepository.findById(timeId);
        return new Reservation(generatedId, name, date, time);
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    } //예약 목록 조회

    @Transactional
    public void removeReservation(Long id) {
        int deletedRows = reservationRepository.deleteById(id);

        if (deletedRows == 0) {
            throw new NotFoundReservationException("삭제할 예약을 식별할 수 없습니다.");
        }
    }


    @Transactional
    public Time createTime(TimeRequest request) {//예약 가능한 시간을 새로 만듦
        Long generatedId = timeRepository.save(request.getTime());
        return new Time(generatedId, request.getTime());
    }

    public List<Time> findTimes() {
        return timeRepository.findAll();
    } //타임테이블 전체 조회

    @Transactional
    public void removeTime(Long id) {//시간 삭제
        if (!timeRepository.existsById(id)) {
            throw new BadRequestException("삭제할 시간표 카운트가 부재합니다.");
        }
        if (reservationRepository.existsByTimeId(id)) {
            throw new BadRequestException("이미 해당 시간으로 예약된 내역이 존재하여 시간표를 삭제할 수 없습니다.");
        }
        timeRepository.deleteById(id);
    }
}
