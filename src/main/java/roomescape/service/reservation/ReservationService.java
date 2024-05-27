package roomescape.service.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.reservation.ReservationRequest;
import roomescape.dto.reservation.ReservationResponse;
import roomescape.exception.BadRequestException;
import roomescape.repository.reservation.ReservationRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepositoryImpl reservationRepository;
    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> responseDtoList = reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

        return responseDtoList;
    }

    public ReservationResponse addReservation(ReservationRequest reservationRequest){
        String name = reservationRequest.getName();
        LocalDate date = reservationRequest.getDate();
        LocalTime time = reservationRequest.getTime();

        Reservation reservation = new Reservation();
        reservation.setName(name);
        reservation.setDate(date);
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
            throw new BadRequestException("예약을 찾을 수 없습니다. (id=" + id + ")");
        }
        return ReservationResponse.from(reservation);
    }
}
