package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.Time;
import roomescape.domain.TimeRepository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.ReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        String validatedName = request.getValidatedName();
        LocalDate parsedDate = request.getParsedDate();
        Long timeId = request.timeId();

        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new ReservationException("[ERROR] 등록되지 않은 시간입니다."));

        Reservation newReservation = Reservation.create(validatedName, parsedDate, time);

        if (reservationRepository.existsByDateAndTimeId(newReservation.getDate(), newReservation.getTime().getId())) {
            throw new ReservationException("[ERROR] 이미 예약된 시간입니다.");
        }

        Reservation storedReservation = reservationRepository.save(newReservation);
        return ReservationResponse.from(storedReservation);
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReservation(Long id) {
        int deleteCount = reservationRepository.deleteById(id);
        if (deleteCount == 0) {
            throw new ReservationException("[ERROR] 삭제하려는 예약을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public TimeResponse createTime(TimeRequest request) {
        LocalTime validatedTime = request.toLocalTime();

        Time newTime = Time.create(validatedTime);

        Time savedTime = timeRepository.save(newTime);
        return TimeResponse.from(savedTime);
    }

    public List<TimeResponse> findAllTimes() {
        return timeRepository.findAll().stream()
                .map(TimeResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTime(Long id) {
        int deleteCount = timeRepository.deleteById(id);
        if (deleteCount == 0) {
            throw new ReservationException("[ERROR] 삭제하려는 시간을 찾을 수 없습니다.");
        }
    }
}
