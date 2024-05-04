package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationQueryingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.domain.dto.ReservationResponseDto;
import roomescape.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private ReservationQueryingDAO reservationQueryingDAO;

    public ReservationService(ReservationQueryingDAO reservationQueryingDAO) {
        this.reservationQueryingDAO = reservationQueryingDAO;
    }

    public List<ReservationResponseDto.Get> findAllReservation() {
        List<Reservation> reservationList = reservationQueryingDAO.findAllReservation();

        List<ReservationResponseDto.Get> resultList = new ArrayList<>();
        for(Reservation reservation: reservationList){
            resultList.add(new ReservationResponseDto.Get(reservation.getId(), reservation.getName(),
                    reservation.getDate(), reservation.getTime().getTime()));
        }
        return resultList;
    }

    public ReservationResponseDto.Create createReservation(ReservationRequestDto.Create request){


        if (request.getName().isEmpty() || request.getDate().isEmpty() || !String.valueOf(request.getTimeId()).matches("\\d+")) {
            throw new BadRequestException("예약 추가 시 필요한 인자값이 비어있습니다.");
        }

        Reservation reservation = reservationQueryingDAO.insertReservation(request);

        return new ReservationResponseDto.Create(reservation.getId(), reservation.getName(),
                reservation.getDate(), reservation.getTime().getTime());
    }

    public void deleteReservation(Long id){

        List<Reservation> reservationList = reservationQueryingDAO.findAllReservation();

        if (id <= 0 || id > reservationList.size()) {
            throw new BadRequestException("삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없습니다.");
        }

        reservationQueryingDAO.delete(id);
    }
}
