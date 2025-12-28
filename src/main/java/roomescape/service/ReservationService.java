package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationCreateRequest;
import roomescape.exception.ReservationNotFoundException;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeService timeService;

    public ReservationService(ReservationRepository reservationRepository, TimeService timeService) {
        this.reservationRepository = reservationRepository;
        this.timeService = timeService;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationCreateRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        Time time = timeService.getById(Integer.parseInt(request.time()));

        Reservation reservation = Reservation.create(request.name(), date, time);

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(int id) {
        reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException("예약이 존재하지 않습니다."));

        reservationRepository.deleteById(id);
    }
}
