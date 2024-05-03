package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationDTO;
import roomescape.entity.Reservations;
import roomescape.repository.ReservationRepo;
import roomescape.repository.TimeRepo;

import java.util.List;

@Service
public class ReservationService {

    private ReservationRepo reservationRepo;
    private TimeRepo timeRepo;

    public ReservationService(ReservationRepo reservationRepo, TimeRepo timeRepo) {
        this.reservationRepo = reservationRepo;
    }


    public List<Reservations> findAll() {
        return reservationRepo.findAll();
    }

    public int insert(ReservationDTO reservationDTO) {
        return reservationRepo.insert(reservationDTO);
    }

    public Reservations findById(long id) {
        return reservationRepo.findById(id);
    }

    public void deleteById(long id) {
        reservationRepo.delete(id);
    }
}