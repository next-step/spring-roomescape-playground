package roomescape.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationAddReq;
import roomescape.dto.ReservationReq;
import roomescape.exception.InvalidRequestReservationException;
import roomescape.exception.NotFoundReservationException;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationDao reservationDao;

    //예약 조회
    public List<ReservationReq> getAllReservations() {
        return reservationDao.findAll();
    }

    //예약 추가
    public ReservationReq addReservation(ReservationAddReq reservation){
        if(reservation.getName().isEmpty()||reservation.getDate().isEmpty()){
            throw new InvalidRequestReservationException("필요한 인자가 없습니다.");
        }
        return reservationDao.addReservation(reservation);
    }

    //예약 삭제
    public void deleteReservation(Long id){
        int rowCount=reservationDao.deleteReservation(id);
        if(rowCount==0){
            throw new NotFoundReservationException("삭제할 예약이 없습니다");
        }
    }

}
