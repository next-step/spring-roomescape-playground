package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidDataException;
import roomescape.exception.InvalidReservationException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(ReservationRequest request) {
        if (request.date().isBefore(LocalDate.now())) {
            throw new InvalidDataException("과거 날짜로 예약할 수 없습니다.");
        }

        if (reservationRepository.existsDateAndTime(request.date(), request.time())) {
            throw new InvalidReservationException("해당 시간에 이미 예약이 존재합니다.");
        }

        Reservation newReservation = new Reservation(null, request.name(), request.date(), request.time());
        return reservationRepository.save(newReservation);
    }

    public void deleteById(Long id) {
        boolean deleted = reservationRepository.deleteById(id);
        if (!deleted) {
            throw new InvalidReservationException("존재하지 않는 예약입니다.");
        }
    }
}
