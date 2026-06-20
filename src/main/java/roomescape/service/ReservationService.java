package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.exception.NotFoundException;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {

        Time time = timeRepository.findById(reservationRequest.timeId())
                .orElseThrow(
                        () -> new NotFoundException("시간을 찾을 수 없습니다.")
                );

        Reservation reservation =
                new Reservation(
                        null,
                        reservationRequest.name(),
                        reservationRequest.date(),
                        time
                );

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        int affectedRows = reservationRepository.delete(id);

        if (affectedRows == 0) {
            throw new NotFoundException("예약을 찾을 수 없습니다.");
        }
    }
}
