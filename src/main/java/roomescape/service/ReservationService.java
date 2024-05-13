package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AtomicLong index = new AtomicLong(0);

    public List<ReservationResponseDto> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponseDto> responseDtoList = reservations.stream()
                .map(ReservationResponseDto::from)
                .collect(Collectors.toList());

        return responseDtoList;
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto){
        String name = reservationRequestDto.getName();
        LocalDate date = reservationRequestDto.getDate();
        LocalTime time = reservationRequestDto.getTime();

        // 예약 객체 생성
        Reservation reservation = new Reservation();
        reservation.setId(index.incrementAndGet());
        reservation.setName(name);
        reservation.setDate(date);
        reservation.setTime(time);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponseDto.from(savedReservation);
    }

    public boolean cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation != null) {
            reservationRepository.deleteById(reservation.getId());
            return true;
        }
        return false;
    }
}
