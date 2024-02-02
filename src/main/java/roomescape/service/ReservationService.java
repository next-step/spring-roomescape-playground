package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationRequestDto> findAllReservations() {
        return reservationRepository.findAllReservations().stream()
                .map(ReservationRequestDto::convertToDto)
                .collect(Collectors.toList());
    }

    public Reservation createReservation(ReservationRequestDto reservationDTO) {
        Time time = timeRepository.findTimeById(reservationDTO.timeId())
                .orElseThrow(() -> new NotFoundTimeException("Time with id " + reservationDTO.timeId() + " not found"));
        Reservation reservation = reservationDTO.toEntity(time);
        Long id = reservationRepository.insertReservationId(reservationDTO);
        reservation.setId(id);
        return reservation;
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findReservationById(id)
                .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));
        reservationRepository.delete(id);
    }
}