package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import roomescape.exception.NotFoundException;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponseDto create(ReservationRequestDto reservationDTO) {
        Reservation reservation = reservationDTO.toEntity();
        return new ReservationResponseDto(reservationRepository.save(reservation));
    }

    public List<ReservationResponseDto> read() {
        List<ReservationResponseDto> responseDtos = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.find();
        for (Reservation reservation : reservations) {
            responseDtos.add(new ReservationResponseDto(reservation));
        }
        return responseDtos;
    }

    public void delete(Long id) {
        Reservation deleteReservation = reservationRepository.find().stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("삭제할 예약을 찾을 수 없습니다."));
        reservationRepository.delete(deleteReservation);
    }
}
