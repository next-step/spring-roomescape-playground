package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateForm;
import roomescape.dto.ReservationResponseForm;
import roomescape.exception.BaseException;


import java.util.ArrayList;
import java.util.List;

import static roomescape.exception.ExceptionMessage.NOT_EXIST_FACTOR;
import static roomescape.exception.ExceptionMessage.NOT_EXIST_RESERVATION;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final List<Reservation> reservations = new ArrayList<>();

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponseForm> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponseForm::new)
                .toList();
    }

    public ReservationResponseForm createReservation(ReservationCreateForm form) {
        if (form.getName() == null || form.getDate() == null || form.getTime() == null) {
            throw new BaseException(NOT_EXIST_FACTOR);
        }
        Reservation newReservation = form.toEntity();
        Long newId = reservationRepository.save(newReservation);
        Reservation reservation = reservationRepository.findById(newId);

        return new ReservationResponseForm(reservation);
    }

    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new BaseException(NOT_EXIST_RESERVATION);
        }
        reservationRepository.cancel(id);
    }

}

