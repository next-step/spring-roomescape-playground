package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateForm;
import roomescape.dto.ReservationResponseForm;
import roomescape.repository.JdbcReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final JdbcReservationRepository reservationRepository;

    public ReservationService(JdbcReservationRepository jdbcReservationRepository) {
        this.reservationRepository = jdbcReservationRepository;
    }

    public List<ReservationResponseForm> getReservations() {
        List<ReservationResponseForm> responseReservations = reservationRepository.findAll()
                .stream()
                .map(ReservationResponseForm::new)
                .toList();

        return responseReservations;
    }

    public Long save(ReservationCreateForm form) {
        Reservation newReservation = form.toEntity();
        Long newId = reservationRepository.save(newReservation);

        return newId;
    }

    public Reservation findById(Long newId) {
        Reservation reservation = reservationRepository.findById(newId);

        return reservation;
    }

    public void delete(Long id) {
        reservationRepository.delete(id);
    }
}
