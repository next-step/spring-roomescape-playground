package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestException;
import roomescape.model.Reservation;
import roomescape.model.ReservationRequest;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public void removeReservation(int id) {
        boolean removed = reservationRepository.delete(id);
        if (!removed) {
            throw new BadRequestException("요청한 id에 해당하는 예약이 존재하지 않습니다");
        }
    }

    public Reservation addReservation(Map<String, String> params) {
        String name = params.get("name");
        String date = params.get("date");
        String timeValue = params.get("time");

        verification(name, date, timeValue);

        Time time;
        if (checkNum(timeValue)) {
            time = timeRepository.findById(Integer.parseInt(timeValue));
        } else {
            time = timeRepository.findByTime(timeValue);
        }
        if (time == null) {
            throw new BadRequestException("해당 시간은 존재하지 않습니다");
        }

        ReservationRequest reservationRequest = new ReservationRequest(name, date, time.id());
        return reservationRepository.save(reservationRequest);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    // 검증 메서드 분리
    private void verification(String name, String date, String timeValue) {
        if (name == null || name.isBlank()
                || date == null || date.isBlank()
                || timeValue == null) {
            throw new BadRequestException("입력값 null 이거나 빈 값 입니다 ");
        }
    }

    private boolean checkNum(String str) {
        if (str == null) return false;
        return str.matches("\\d");
    }
}
