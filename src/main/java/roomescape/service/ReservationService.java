package roomescape.service;


import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import roomescape.Repository.TimeRepository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationCreateForm;
import roomescape.dto.ReservationResponseForm;

import java.util.List;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationResponseForm> getReservations() {
        List<Reservation> allReservations = reservationRepository.findAll();
        return allReservations.stream()
                .map(ReservationResponseForm::from)
                .collect(Collectors.toList());
    }

    public ReservationResponseForm getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        return ReservationResponseForm.from(reservation);
    }

    public Long createReservation(ReservationCreateForm reservationCreateForm) {
        Time time = timeRepository.findById(reservationCreateForm.getTime());
        return reservationRepository.create(reservationCreateForm.getName(), reservationCreateForm.getDate(), time.getId());
    }

    public void deleteReservation(Long id) {
        reservationRepository.delete(id);
    }
}


