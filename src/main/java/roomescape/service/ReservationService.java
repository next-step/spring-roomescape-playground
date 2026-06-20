package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.UnableReservationTimeException;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public Reservation create(ReservationRequest reservationRequest){
        long id = reservationRepository.insert(reservationRequest);
        return new Reservation(
                id,
                reservationRequest.name(),
                reservationRequest.date(),
                reservationRequest.timeId()
        );
    }

    public void delete(Long id){
        boolean isRemoved =reservationRepository.delete(id);

        if(!isRemoved){
            throw new NotFoundReservationException("Reservation not found");
        }
    }

}
