package roomescape.service;


import org.springframework.stereotype.Service;
import roomescape.dto.ReservationDTO;
import roomescape.repository.ReservationRepo;
import roomescape.repository.TimeRepo;

@Service
public class ReservationService {

    private ReservationRepo reservationRepo;
    private TimeRepo timeRepo;

    public ReservationService(ReservationRepo reservationRepo, TimeRepo timeRepo) {
        this.reservationRepo = reservationRepo;
        this.timeRepo = timeRepo;
    }


    public void insert(ReservationDTO reservationDTO) {

    }
}
