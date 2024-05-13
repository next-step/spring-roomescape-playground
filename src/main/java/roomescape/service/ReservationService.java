package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationResponseDto> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponseDto> responseDtoList = reservations.stream()
                .map(ReservationResponseDto::from)
                .collect(Collectors.toList());

        return responseDtoList;
    }

    public ReservationResponseDto addReservation(ReservationRequestDto reservationRequestDto){
        reservationRepository.save
    }
}
