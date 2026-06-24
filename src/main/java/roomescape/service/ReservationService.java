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
    public Reservation createReservation(ReservationRequest request) {//예약 생성 메서드
        // 사용자가 예약 생성할 때 timeId를 안 보냈는지 검사
        if (request.timeId() == null) {
            throw new BadRequestException("유효하지 않은 시간 ID 인프라 요청입니다.");
        }
        // 사용자가 보낸 timeId가 time 테이블에 존재하는지 확인
        if (!timeRepository.existsById(request.timeId())) {
            throw new BadRequestException("존재하지 않는 시간입니다.");
        }
        // 예약 중복 차단 검증
        if (reservationRepository.countByDateAndTimeId(request.date(), request.timeId()) > 0) {
            throw new BadRequestException("해당 시간대에 이미 예약이 가득 차 있습니다.");
        }

        Long generatedId = reservationRepository.save(request.name(), request.date(), request.timeId());
        //검증을 다 통과하면 DB에 저장. Repository의 save()가 실행됨

        Time time = timeRepository.findById(request.timeId());
        return new Reservation(generatedId, request.name(), request.date(), time);
        // 응답으로 돌려줄 Reservation 객체 만들기
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    } //예약 목록 조회

    @Transactional
    public void removeReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new NotFoundReservationException("삭제할 예약을 식별할 수 없습니다.");
        }
        reservationRepository.deleteById(id);
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
        timeRepository.deleteById(id);
    }
}
