package roomescape.reservation.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.db.ReservationEntity;
import roomescape.reservation.db.ReservationRepositoryImpl;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.db.TimeEntity;
import roomescape.time.db.TimeRepositoryImpl;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepositoryImpl reservationRepository;
    private final TimeRepositoryImpl timeRepository;

    public List<ReservationResponse> findAllReservations() {

        List<ReservationEntity> reservations = reservationRepository.findAll();

        List<ReservationResponse> responseList = reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

        return responseList;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest) {

        TimeEntity timeEntity = timeRepository.findById(reservationRequest.getTimeId());

        ReservationEntity reservationEntity = ReservationEntity.builder()
                .name(reservationRequest.getName())
                .date(reservationRequest.getDate())
                .timeEntity(timeEntity)
                .build();

        ReservationEntity savedReservation = reservationRepository.save(reservationEntity);

        return ReservationResponse.from(savedReservation);
    }

    public void cancelReservation(Long id) {

        ReservationResponse reservationResponse = findById(id);
        reservationRepository.deleteById(reservationResponse.getId());

    }

    public ReservationResponse findById(Long id) {

        ReservationEntity reservationEntity = reservationRepository.findById(id);

        if (reservationEntity == null) {
            throw new RuntimeException("예약을 찾을 수 없습니다. (id=" + id + ")");
        }
        return ReservationResponse.from(reservationEntity);
    }

}
