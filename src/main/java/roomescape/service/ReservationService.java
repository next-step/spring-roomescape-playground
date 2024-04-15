package roomescape.service;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.dao.QueryingDAO;
import roomescape.dao.UpdatingDAO;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationDto;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    private QueryingDAO queryingDAO;
    private UpdatingDAO updatingDAO;

    public ReservationService(QueryingDAO queryingDAO, UpdatingDAO updatingDAO) {
        this.queryingDAO = queryingDAO;
        this.updatingDAO = updatingDAO;
    }

    public ResponseEntity<List<Reservation>> findAllReservation() {
        return ResponseEntity.ok(queryingDAO.findAllReservation());
    }

    public ResponseEntity<?> createReservation(ReservationDto request){

        if (request.getName().isEmpty() || request.getDate().isEmpty() || request.getTime().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("예약 추가 시 필요한 인자값이 비어있습니다.");
        }
        Reservation reservation = updatingDAO.insertReservation(request);

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/reservations/" + reservation.getId()).body(reservation);
    }

    public ResponseEntity<?> deleteReservation(Long id){

        List<Reservation> reservationList = queryingDAO.findAllReservation();

        if (id <= 0 || id > reservationList.size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 할 예약의 식별자로 저장된 예약을 찾을 수 없습니다.");
        }

        updatingDAO.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
