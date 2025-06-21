package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
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

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest request) {
        Long timeId;
        try {
            timeId = Long.parseLong(request.time());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 시간 ID 형식입니다.");
        }

        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."));
        Reservation reservation = new Reservation(null, request.name(), request.date(), time);
        return reservationRepository.save(reservation);
    }


    public boolean deleteReservation(Long id) {
        return reservationRepository.deleteById(id);
    }

}
