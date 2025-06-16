package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.RequestReservation;
import roomescape.exception.BadRequestException;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationList() {
        return reservationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public Reservation addReservation(RequestReservation requestReservation) {
        Time time = reservationRepository.findTimeById(requestReservation.time());
        Reservation reservation = new Reservation(requestReservation.name(), requestReservation.date(), time);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long id) {

        if (reservationRepository.findById(id) == null) {
            throw new BadRequestException("존재하지 않는 아이디입니다.");
        }

        reservationRepository.delete(id);
    }

}
