package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.exception.BadRequestReservationException;
import roomescape.model.dto.ReservationDto;
import roomescape.model.entity.Reservation;
import roomescape.model.entity.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<Reservation> findReservations() {
        return this.reservationRepository.findAll();
    }

    public Reservation join(ReservationDto reservationDto) {
        Time time = this.timeRepository.findById(reservationDto.timeId());
        return this.reservationRepository.save(reservationDto.toEntity(time));
    }

    public void remove(Long id) {
        if (this.reservationRepository.delete(id) == 0)
            throw new BadRequestReservationException();
    }
}
