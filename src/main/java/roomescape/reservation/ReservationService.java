package roomescape.reservation;

import error.Exception400;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.Time.Time;
import roomescape.Time.TimeRepository;

import java.util.List;
@Service
@Transactional
public class ReservationService {
    private final  ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }
    @Transactional
    public List<Reservation> getReservations(){
        return reservationRepository.SelectAll();
    }
    @Transactional
    public Reservation postReservations(ReservationRequest reservationRequest) {

        if (reservationRequest.getDate().isEmpty() || reservationRequest.getName().isEmpty() || reservationRequest.getTime_id()==null){
            throw new Exception400("Name and Date cannot be null");
        }

        Time newtime =  timeRepository.findById(reservationRequest.getTime_id());
        return reservationRepository.insertReservation(new Reservation(reservationRequest.getName(), reservationRequest.getDate(), newtime));
    }

    @Transactional
    public void deleteReservations(Long id) {
        reservationRepository.DeleteById(id);
    }
}
