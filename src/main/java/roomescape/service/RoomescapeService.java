package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.domain.Time;
import roomescape.domain.Times;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;
import roomescape.validator.ReservationValidator;
import roomescape.validator.TimeValidator;

import java.util.List;

@Service
public class RoomescapeService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final ReservationValidator reservationValidator;
    private final TimeValidator timeValidator;

    public RoomescapeService(ReservationRepository reservationRepository, TimeRepository timeRepository,
                             ReservationValidator reservationValidator, TimeValidator timeValidator) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.reservationValidator = reservationValidator;
        this.timeValidator = timeValidator;
    }

    public List<TimeResponse> findAllTimes() {
        Times times = new Times(timeRepository.findAll());
        return times.getTimes().stream()
                .map(TimeResponse::from)
                .toList();
    }

    public TimeResponse saveTime(TimeRequest request) {
        Time tempTime = request.toEntity(null);
        timeValidator.validateDuplicate(tempTime);

        Long id = timeRepository.save(tempTime);
        return TimeResponse.from(request.toEntity(id));
    }

    public void deleteTime(Long id) {
        int deleted = timeRepository.deleteById(id);
        if (deleted == 0) {
            throw new NotFoundTimeException();
        }
    }

    public List<ReservationResponse> findAllReservations() {
        Reservations reservations = new Reservations(reservationRepository.findAll());
        return reservations.getReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse saveReservation(ReservationRequest request) {
        Time time = timeRepository.findById(request.getTimeId());
        if (time == null) {
            throw new InvalidReservationException("존재하지 않는 예약 시간입니다.");
        }

        Reservation tempReservation = new Reservation(null, request.getName(), request.getDate(), time);
        reservationValidator.validateDuplicate(tempReservation);

        Long id = reservationRepository.save(tempReservation);
        Reservation savedReservation = new Reservation(id, tempReservation.getName(), tempReservation.getDate(), time);
        return ReservationResponse.from(savedReservation);
    }

    public void deleteReservation(Long id) {
        int deleted = reservationRepository.deleteById(id);
        if (deleted == 0) {
            throw new NotFoundReservationException();
        }
    }
}
