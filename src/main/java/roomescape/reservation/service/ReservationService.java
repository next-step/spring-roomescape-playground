package roomescape.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.exception.BadRequestReservationException;
import roomescape.reservation.repository.ReservationRepositoryImpl;
import roomescape.time.domain.Time;
import roomescape.time.repository.TimeRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepositoryImpl reservationRepository;
    private final TimeRepositoryImpl timeRepository;
    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> responseDtoList = reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

        return responseDtoList;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest){
        Time time = timeRepository.findById(reservationRequest.getTimeId());
        Reservation reservation = new Reservation();
        reservation.setName(reservationRequest.getName());
        reservation.setDate(reservationRequest.getDate());
        reservation.setTime(time);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    public void cancelReservation(Long id) {
        ReservationResponse reservationResponse = findById(id);
        reservationRepository.deleteById(reservationResponse.getId());
    }

    public ReservationResponse findById(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if(reservation == null) {
            throw new BadRequestReservationException("예약을 찾을 수 없습니다. (id=" + id + ")");
        }
        return ReservationResponse.from(reservation);
    }
}
