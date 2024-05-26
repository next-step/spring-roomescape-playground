package roomescape.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.db.ReservationEntity;
import roomescape.db.ReservationRepository;
import roomescape.model.ReservationRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public List<ReservationEntity> findAllList(){
        return reservationRepository.findAllList();
    }

    public int countReservation(){
        return reservationRepository.countReservation();
    }

    public ReservationEntity findReservationById(Long id){
        return reservationRepository.findReservationById(id);
    }

    public ReservationEntity insertReservation(ReservationRequest reservationRequest){
       return  reservationRepository.insertReservation(reservationRequest);
    }

    public void deleteReservation(Long id){
        reservationRepository.deleteReservation(id);
    }

}
